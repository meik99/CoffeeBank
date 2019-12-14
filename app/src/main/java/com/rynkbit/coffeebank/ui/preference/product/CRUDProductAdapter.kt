package com.rynkbit.coffeebank.ui.preference.product

import android.app.AlertDialog
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Product
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class CRUDProductAdapter : RecyclerView.Adapter<CRUDProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtID: TextView = itemView.findViewById(R.id.txtID)
        val editName: EditText = itemView.findViewById(R.id.editName)
        val editPrice: EditText = itemView.findViewById(R.id.editPrice)
        val editStock: EditText = itemView.findViewById(R.id.editStock)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
    }

    private var products = listOf<Product>()

    var onProductDelete: ((Product) -> Unit)? = null
    var onProductChange: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crudproduct, parent, false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.txtID.text = product.uid.toString()
        holder.editName.setText(product.name)
        holder.editPrice.setText(
            NumberFormat
                .getNumberInstance(Locale.getDefault())
                .format(
                    BigDecimal(product.price).setScale(2, RoundingMode.HALF_UP)))
        holder.editStock.setText(
            product.stock.toString())

        setupTextChangedListener(holder, product)
        setupButtonClickListener(holder, product)
    }

    private fun setupButtonClickListener(holder: ViewHolder, product: Product) {
        holder.btnRemove.setOnClickListener {
            showConfirmationDialog(it.context, product)
        }
    }

    private fun showConfirmationDialog(context: Context, product: Product) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(context.getString(
                R.string.confirm_product_delete, product.name))
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                onProductDelete?.invoke(product)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun setupTextChangedListener(
        holder: ViewHolder,
        product: Product
    ) {
        holder.editName.addTextChangedListener {
            onProductChange?.invoke(
                Product(
                    uid = product.uid,
                    name = it?.toString() ?: "",
                    price = product.price,
                    stock = product.stock
                )
            )
        }

        holder.editPrice.addTextChangedListener {
            onProductChange?.invoke(
                Product(
                    uid = product.uid,
                    name = product.name,
                    price = it.toString().toDoubleOrNull() ?: 0.0,
                    stock = product.stock
                )
            )
        }

        holder.editStock.addTextChangedListener {
            onProductChange?.invoke(
                Product(
                    uid = product.uid,
                    name = product.name,
                    price = product.price,
                    stock = it.toString().toIntOrNull() ?: 0
                )
            )
        }
    }

    fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}