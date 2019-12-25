package com.rynkbit.coffeebank.ui.preference.customer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.data.CustomerFacade

class CreateCustomerDialog(context: Context?) : AlertDialog(context) {

    val customer: LiveData<Customer>
        get() = _customer

    private val _customer = MutableLiveData<Customer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add_customer)

        val editFirstname: EditText = findViewById(R.id.editFirstname)
        val editLastname: EditText = findViewById(R.id.editLastname)
        val editBalance: EditText = findViewById(R.id.editBalance)
        val btnCancel: Button = findViewById(R.id.btnCancel)
        val btnSave: Button = findViewById(R.id.btnSave)

        btnCancel.setOnClickListener { dismiss() }
        btnSave.setOnClickListener {
            _customer.postValue(Customer(
                    uid = 0,
                    firstname = editFirstname.text.toString(),
                    lastname = editLastname.text.toString(),
                    balance = editBalance.text.toString().toDoubleOrNull() ?: 0.0
                ))
            dismiss()
        }
    }
}