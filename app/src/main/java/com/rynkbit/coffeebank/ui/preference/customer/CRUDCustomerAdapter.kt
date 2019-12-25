package com.rynkbit.coffeebank.ui.preference.customer

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.formatter.DecimalFormatter

class CRUDCustomerAdapter : RecyclerView.Adapter<CRUDCustomerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtID: TextView = itemView.findViewById(R.id.txtID)
        val editFirstname: EditText = itemView.findViewById(R.id.editFirstname)
        val editLastname: EditText = itemView.findViewById(R.id.editLastname)
        val editBalance: EditText = itemView.findViewById(R.id.editBalance)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
    }

    var customers = listOf<Customer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    var onCustomerChange: ((Customer) -> Unit)? = null
    var onCustomerDelete: ((Customer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crudcustomer, parent, false))
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customers[position]

        holder.txtID.text = customer.uid.toString()
        holder.editFirstname.setText(customer.firstname)
        holder.editLastname.setText(customer.lastname)
        holder.editBalance.setText(DecimalFormatter().format(customer.balance))

        setupTextChangedListener(holder, customer)
        setupButtonClickListener(holder, customer)
    }

    private fun setupButtonClickListener(
        holder: ViewHolder,
        customer: Customer
    ) {
        holder.btnRemove.setOnClickListener {
            showConfirmationDialog(holder.itemView.context, customer)
        }
    }

    private fun showConfirmationDialog(context: Context, customer: Customer) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(context.getString(
                R.string.confirm_customer_delete, customer.firstname, customer.lastname))
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                onCustomerDelete?.invoke(customer)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun setupTextChangedListener(
        holder: ViewHolder,
        customer: Customer
    ) {
        holder
            .editFirstname
            .addTextChangedListener {
                onCustomerChange?.invoke(
                    Customer(
                        customer.uid,
                        it?.toString() ?: String(),
                        customer.lastname,
                        customer.balance
                    )
                )
            }

        holder
            .editLastname
            .addTextChangedListener {
                onCustomerChange?.invoke(
                    Customer(
                        customer.uid,
                        customer.firstname,
                        it?.toString() ?: String(),
                        customer.balance
                    )
                )
            }

        holder
            .editBalance
            .addTextChangedListener {
                val balance =
                    if(it.toString().isNotEmpty()) {
                        DecimalFormatter().parse(it.toString())
                    }else{
                        0.0
                    }

                onCustomerChange?.invoke(
                    Customer(
                        customer.uid,
                        customer.firstname,
                        customer.lastname,
                        balance
                    )
                )
            }
    }
}