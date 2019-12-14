package com.rynkbit.coffeebank.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Product
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.NumberFormat
import java.util.*

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtStock: TextView = itemView.findViewById(R.id.txtStock)
    }

    private var products = listOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = products[position].name
        holder.txtPrice.text =
            NumberFormat
                .getCurrencyInstance(Locale.getDefault())
                .format(products[position].price)
        holder.txtStock.text = products[position].stock.toString()
    }

    fun updateProducts(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }
}