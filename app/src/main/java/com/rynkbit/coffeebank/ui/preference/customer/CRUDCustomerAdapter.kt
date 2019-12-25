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
import com.rynkbit.coffeebank.ui.preference.customer.color.ColorPickerDialog
import kotlinx.android.synthetic.main.item_crudcustomer.view.*

class CRUDCustomerAdapter : RecyclerView.Adapter<CRUDCustomerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtID: TextView = itemView.txtID
        val editFirstname: EditText = itemView.editFirstname
        val editLastname: EditText = itemView.editLastname
        val editBalance: EditText = itemView.editBalance
        val btnRemove: Button = itemView.btnRemove
        val btnColor: Button = itemView.btnColor
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
        holder.btnColor.setBackgroundColor(customer.color)

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
        holder.btnColor.setOnClickListener {
            showColorPickerDialog(holder, customer)
        }
    }

    override fun getItemId(position: Int): Long {
        return customers[position].uid
    }

    private fun showColorPickerDialog(holder: ViewHolder, customer: Customer) {
        val colorPicker = ColorPickerDialog(holder.itemView.context, customer)
        colorPicker.setOnDismissListener {
            holder.btnColor.setBackgroundColor(customer.color)
            onCustomerChange?.invoke(customer)
        }
        colorPicker.show()
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
                        customer.balance,
                        customer.color
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
                        customer.balance,
                        customer.color
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
                        balance,
                        customer.color
                    )
                )
            }
    }
}