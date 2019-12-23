package com.rynkbit.coffeebank.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Product
import kotlinx.android.synthetic.main.item_product.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtStock: TextView = itemView.findViewById(R.id.txtStock)
    }

    private var products = listOf<Product>()

    var onClickProduct: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val numberFormat = NumberFormat
            .getNumberInstance(Locale.getDefault())
        numberFormat.minimumFractionDigits = 2

        holder.txtName.text = products[position].name
        holder.txtPrice.text =
            numberFormat
                .format(
                    BigDecimal(products[position].price).setScale(2, RoundingMode.HALF_UP))
        holder.txtStock.text = products[position].stock.toString()

        holder.itemView.setOnClickListener {
            onClickProduct?.invoke(products[position])
        }
    }

    fun updateProducts(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }
}