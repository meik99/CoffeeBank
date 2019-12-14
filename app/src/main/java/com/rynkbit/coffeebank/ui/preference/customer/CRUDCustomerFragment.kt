package com.rynkbit.coffeebank.ui.preference.customer


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import io.reactivex.Maybe
import kotlinx.android.synthetic.main.fragment_crudcustomer.*
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.fragment_customer.listCustomer

/**
 * A simple [Fragment] subclass.
 */
class CRUDCustomerFragment : Fragment() {

    lateinit var crudCustomerAdapter: CRUDCustomerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crudcustomer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupCustomerAdapter()
        setupCustomerList()
        setupAddButton()
    }

    private fun setupCustomerAdapter() {
        crudCustomerAdapter = CRUDCustomerAdapter()
        crudCustomerAdapter.onCustomerChange =
            CustomerFacade(AppDatabase.getInstance(context!!))
                .updateFun()
        crudCustomerAdapter.onCustomerDelete = {
            CustomerFacade(AppDatabase.getInstance(context!!))
                .delete(it)
                .subscribe { t1, t2 ->
                    updateCustomerList()
                }
        }
    }

    private fun setupAddButton() {
        fabAdd.setOnClickListener {
            val dialog = CreateCustomerDialog(context)
            dialog.setTitle(R.string.add_customer)
            dialog.setOnDismissListener { updateCustomerList() }
            dialog.show()
        }
    }


    override fun onResume() {
        super.onResume()

        updateCustomerList()
    }

    private fun updateCustomerList(){
        CustomerFacade(AppDatabase.getInstance(context!!))
            .getAll(2000, 0)
            .map {
                activity?.runOnUiThread {
                    crudCustomerAdapter.updateCustomers(it)
                }
            }
            .subscribe()
    }

    private fun setupCustomerList() {
        listCustomer.layoutManager =
            LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        listCustomer.adapter = crudCustomerAdapter
    }
}
