package com.rynkbit.coffeebank.ui.preference.product


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.ProductFacade
import kotlinx.android.synthetic.main.fragment_crudcustomer.*
import kotlinx.android.synthetic.main.fragment_product.*

/**
 * A simple [Fragment] subclass.
 */
class CRUDProductFragment : Fragment() {

    lateinit var crudProductAdapter: CRUDProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crudproduct, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupProductAdapter()
        setupProductList()
        setupAddButton()
    }

    private fun setupAddButton() {
        fabAdd.setOnClickListener {
            val dialog = CreateProductDialog(context)
            dialog.setOnDismissListener { updateProductList() }
            dialog.show()
        }
    }


    private fun setupProductAdapter() {
        crudProductAdapter = CRUDProductAdapter()
        crudProductAdapter.onProductChange =
            ProductFacade(AppDatabase.getInstance(context!!))
                .updateFun()
        crudProductAdapter.onProductDelete = {
            ProductFacade(AppDatabase.getInstance(context!!))
                .delete(it)
                .subscribe { t1, t2 ->
                    updateProductList()
                }
        }
    }

    private fun setupProductList() {
        listProducts.adapter = crudProductAdapter
        listProducts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        updateProductList()
    }

    private fun updateProductList() {
        ProductFacade(AppDatabase.getInstance(context!!))
            .getAll(1000, 0)
            .map {
                activity?.runOnUiThread {
                    crudProductAdapter.updateProducts(it)
                }
            }
            .subscribe()
    }
}
