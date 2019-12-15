package com.rynkbit.coffeebank.ui.preference.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.TransactionFacade
import kotlinx.android.synthetic.main.fragment_crudtransaction.*

class CRUDTransactionFragment : Fragment() {
    lateinit var crudTransactionAdapter: CRUDTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crudtransaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupTransactionAdapter()
        setupTransactionList()
        updateTransactions()
    }

    private fun setupTransactionAdapter() {
        crudTransactionAdapter = CRUDTransactionAdapter()
        crudTransactionAdapter.onTransactionDelete = {
            TransactionFacade(AppDatabase.getInstance(context!!))
                .delete(it)
                .blockingGet()

            updateTransactions()
        }
    }

    private fun updateTransactions() {
        TransactionFacade(AppDatabase.getInstance(context!!))
            .getAll()
            .map {
                activity?.runOnUiThread {
                    crudTransactionAdapter.updateTransactions(it.sortedByDescending { it.date })
                }
            }
            .subscribe()
    }

    private fun setupTransactionList() {
        listTransactions.adapter = crudTransactionAdapter
        listTransactions.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
