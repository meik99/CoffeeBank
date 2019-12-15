package com.rynkbit.coffeebank.ui.product


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.logic.data.ProductFacade
import com.rynkbit.coffeebank.logic.data.TransactionFacade
import com.rynkbit.coffeebank.ui.CustomerProductViewModel
import com.rynkbit.coffeebank.ui.ResponsiveStaggeredGridLayoutManager
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_product.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {
    lateinit var productAdapter: ProductAdapter
    lateinit var customerProductViewModel: CustomerProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        customerProductViewModel = ViewModelProviders.of(activity!!)[CustomerProductViewModel::class.java]

        setupAdapter()
        setupProductList()
    }

    private fun setupAdapter() {
        productAdapter = ProductAdapter()
        productAdapter.onClickProduct = { product ->
            if(product.stock > 0) {
                showBuyDialog(product)
            } else {
                showNoStockSnackbar()
            }
        }
    }

    private fun showNoStockSnackbar() {
        Snackbar
            .make(view!!, R.string.not_stocked, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showBuyDialog(product: Product) {
        AlertDialog.Builder(context)
            .setTitle(R.string.buy)
            .setMessage(getString(R.string.buy_product, product.name))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                TransactionFacade(AppDatabase.getInstance(context!!))
                    .buy(customerProductViewModel.customer!!, product)
                    .subscribe { t1, t2 ->
                        Navigation.findNavController(activity!!, R.id.navhost)
                            .popBackStack()
                    }
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupProductList() {
        listProducts.adapter = productAdapter
        listProducts.layoutManager =
            ResponsiveStaggeredGridLayoutManager(context!!, LinearLayoutManager.VERTICAL)
    }

    override fun onResume() {
        super.onResume()

        updateProducts()
    }

    private fun updateProducts() {
        ProductFacade(AppDatabase.getInstance(context!!))
            .getAll(1000, 0)
            .subscribeOn(Schedulers.newThread())
            .map {
                activity!!.runOnUiThread {
                    productAdapter.updateProducts(it.sortedBy { it.name })
                }
            }
            .subscribe()
    }
}
