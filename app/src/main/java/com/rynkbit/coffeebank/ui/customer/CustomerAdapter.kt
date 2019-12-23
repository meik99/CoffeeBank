package com.rynkbit.coffeebank.ui.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Customer
import kotlinx.android.synthetic.main.item_customer.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFirstname: TextView = itemView.findViewById(R.id.txtFirstname)
        val txtLastname: TextView = itemView.findViewById(R.id.txtLastname)
        val txtBalance: TextView = itemView.findViewById(R.id.txtBalance)
    }

    var onClickListener: ((Customer) -> Unit)? = null

    private var customers: List<Customer> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_customer, parent, false)
        )
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val numberFormat = NumberFormat
            .getNumberInstance(Locale.getDefault())
        numberFormat.minimumFractionDigits = 2

        holder.txtFirstname.text = customers[position].firstname
        holder.txtLastname.text = customers[position].lastname
        holder.txtBalance.text =
            numberFormat
                .format(BigDecimal(customers[position].balance)
                    .setScale(2, RoundingMode.HALF_UP))

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(customers[position])
        }
    }

    fun updateCustomers(it: List<Customer>) {
        customers = it
        notifyDataSetChanged()
    }
}