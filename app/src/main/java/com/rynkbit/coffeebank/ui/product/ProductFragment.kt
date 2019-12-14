package com.rynkbit.coffeebank.ui.product


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.ProductFacade
import com.rynkbit.coffeebank.ui.ResponsiveStaggeredGridLayoutManager
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_product.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {
    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        productAdapter = ProductAdapter()

        listProducts.adapter = productAdapter
        listProducts.layoutManager =
            ResponsiveStaggeredGridLayoutManager(context!!, LinearLayoutManager.VERTICAL)
    }

    override fun onResume() {
        super.onResume()

        ProductFacade(AppDatabase.getInstance(context!!))
            .getAll(1000, 0)
            .subscribeOn(Schedulers.newThread())
            .map {
                activity!!.runOnUiThread {
                    productAdapter.updateProducts(it)
                }
            }
            .subscribe()
    }
}
