package com.rynkbit.coffeebank.ui.preference.customer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import kotlinx.android.synthetic.main.fragment_crudcustomer.*
import kotlinx.android.synthetic.main.fragment_customer.listCustomer

/**
 * A simple [Fragment] subclass.
 */
class CRUDCustomerFragment : Fragment() {

    private lateinit var crudCustomerAdapter: CRUDCustomerAdapter
    private lateinit var viewmodel: CRUDCustomerViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crudcustomer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewmodel()
        setupCustomerAdapter()
        setupCustomerList()
        setupAddButton()
    }

    private fun setupViewmodel() {
        val factory = CRUDCustomerViewmodelFactoy(
            CustomerFacade(
                AppDatabase.getInstance(context!!)
            )
        )
        viewmodel =
            ViewModelProviders.of(this, factory)[CRUDCustomerViewmodel::class.java]
    }

    private fun setupCustomerAdapter() {
        crudCustomerAdapter = CRUDCustomerAdapter()
        crudCustomerAdapter.onCustomerChange = {
            viewmodel.updateCustomer(it)
        }
        crudCustomerAdapter.onCustomerDelete = {
            viewmodel.deleteCustomer(it)
        }

        viewmodel
            .customers
            .observe(this, Observer {
                crudCustomerAdapter.customers = it
            })
    }

    private fun setupAddButton() {
        fabAdd.setOnClickListener {
            val dialog = CreateCustomerDialog(context)
            dialog.setTitle(R.string.add_customer)
            dialog.show()
            dialog.customer.observe(this, Observer {
                viewmodel.addCustomer(it)
            })
        }
    }

    private fun setupCustomerList() {
        listCustomer.adapter = crudCustomerAdapter
    }
}
