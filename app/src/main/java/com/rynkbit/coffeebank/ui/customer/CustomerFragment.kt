package com.rynkbit.coffeebank.ui.customer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import com.rynkbit.coffeebank.ui.CustomerProductViewModel
import com.rynkbit.coffeebank.ui.ResponsiveStaggeredGridLayoutManager
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_customer.*

/**
 * A simple [Fragment] subclass.
 */
class CustomerFragment : Fragment() {
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerProductViewModel: CustomerProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        customerProductViewModel = ViewModelProviders.of(this)[CustomerProductViewModel::class.java]

        createCustomerAdapter()
        setupCustomerList()
    }

    override fun onResume() {
        super.onResume()

        CustomerFacade(AppDatabase.getInstance(context!!))
            .getAll(1000, 0)
            .subscribeOn(Schedulers.newThread())
            .map {
                activity?.runOnUiThread {
                    customerAdapter.updateCustomers(it)
                }
            }
            .subscribe()
    }

    private fun setupCustomerList() {
        listCustomer.layoutManager = ResponsiveStaggeredGridLayoutManager(
            context!!, LinearLayoutManager.VERTICAL
        )
        listCustomer.adapter = customerAdapter
    }

    private fun createCustomerAdapter() {
        customerAdapter = CustomerAdapter()
        customerAdapter.onClickListener = onCustomerClick()
    }

    private fun onCustomerClick(): ((Customer) -> Unit)? = {customer ->
        customerProductViewModel.customer = customer

        Navigation
            .findNavController(activity!!, R.id.navhost)
            .navigate(R.id.action_customerFragment_to_productFragment)
    }
}
