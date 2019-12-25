package com.rynkbit.coffeebank.ui.preference.product

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.logic.data.ProductFacade
import kotlinx.android.synthetic.main.dialog_add_product.*

class CreateProductDialog(context: Context?) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_product)

        btnCancel.setOnClickListener { dismiss() }
        btnSave.setOnClickListener {
            ProductFacade(AppDatabase.getInstance(context))
                .insert(Product(
                    uid = 0,
                    name = editName.text.toString(),
                    price = editPrice.text.toString().toDoubleOrNull() ?: 0.0,
                    stock = editStock.text.toString().toIntOrNull() ?: 0
                ))
                .blockingGet()
            dismiss()
        }

        window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
    }
}