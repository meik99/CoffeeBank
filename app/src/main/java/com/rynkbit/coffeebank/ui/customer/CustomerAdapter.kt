package com.rynkbit.coffeebank.ui.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.formatter.DecimalFormatter
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
        val customer = customers[position]

        holder.txtFirstname.text = customer.firstname
        holder.txtLastname.text = customer.lastname
        holder.txtBalance.text = DecimalFormatter().format(customer.balance)
        holder.itemView.setBackgroundColor(customer.color)

        holder.itemView.setOnClickListener {
            onClickListener?.invoke(customer)
        }
    }

    fun updateCustomers(it: List<Customer>) {
        customers = it
        notifyDataSetChanged()
    }
}