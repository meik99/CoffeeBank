package com.rynkbit.coffeebank.ui.preference.product

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.logic.data.ProductFacade
import kotlinx.android.synthetic.main.dialog_add_product.*

class CreateProductDialog(context: Context?) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_product)

//        val editName: EditText = findViewById(R.id.editName)
//        val editPrice: EditText = findViewById(R.id.editPrice)
//        val editStock: EditText = findViewById(R.id.editStock)
//        val btnCancel: Button = findViewById(R.id.btnCancel)
//        val btnSave: Button = findViewById(R.id.btnSave)

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
    }
}