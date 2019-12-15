package com.rynkbit.coffeebank.ui.preference.transaction

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

class CRUDTransactionAdapter : RecyclerView.Adapter<CRUDTransactionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val txtFirstname: TextView = itemView.findViewById(R.id.txtFirstname)
        val txtLastname: TextView = itemView.findViewById(R.id.txtLastname)
        val txtProduct: TextView = itemView.findViewById(R.id.txtProduct)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val btnRemove: TextView = itemView.findViewById(R.id.btnRemove)
    }

    private var transactions = listOf<Transaction>()

    var onTransactionDelete: ((Transaction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crudtransaction, parent, false))
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.txtDate.text =
            DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(transaction.date)
        holder.txtFirstname.text = transaction.customerFirstname
        holder.txtLastname.text = transaction.customerLastname
        holder.txtProduct.text = transaction.productName
        holder.txtPrice.text =
            NumberFormat
                .getCurrencyInstance(Locale.getDefault())
                .format(transaction.productPrice)
        holder.btnRemove.setOnClickListener {
            showConfirmationDialog(it.context, transaction)
        }
    }

    private fun showConfirmationDialog(context: Context, transaction: Transaction) {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete)
            .setMessage(context.getString(
                R.string.confirm_transaction_delete,
                transaction.customerFirstname,
                transaction.customerLastname,
                transaction.productName))
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                onTransactionDelete?.invoke(transaction)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    fun updateTransactions(transactions: List<Transaction>){
        this.transactions = transactions
        notifyDataSetChanged()
    }

}