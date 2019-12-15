package com.rynkbit.coffeebank.ui.customer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.preference.PreferenceManager
import com.rynkbit.coffeebank.R
import kotlinx.android.synthetic.main.dialog_password.*

class PasswordDialog(context: Context) : AlertDialog(context) {
    var onPasswordCorrect: (() -> Unit)? = null
    var onPasswordIncorrect: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_password)
        setTitle(R.string.password)

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            dismiss()
            val password = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("password", "")
            val input = editPassword.text.toString()

            if(input == password){
                onPasswordCorrect?.invoke()
            } else {
                onPasswordIncorrect?.invoke()
            }
        }
    }
}