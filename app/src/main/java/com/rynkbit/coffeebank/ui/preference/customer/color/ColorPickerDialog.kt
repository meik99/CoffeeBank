package com.rynkbit.coffeebank.ui.preference.customer.color

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.databinding.DialogColorPickerBinding
import com.rynkbit.coffeebank.db.entitiy.Customer

class ColorPickerDialog(
    context: Context,
    val customer: Customer
) : AlertDialog(context){
    private lateinit var binding: DialogColorPickerBinding

    var onColorSelected: ((Customer) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogColorPickerBinding.inflate(LayoutInflater.from(context))
        binding.pickedColor = PickedColor.fromColor(customer.color)
        binding.dialog = this

        setContentView(binding.root)
        setTitle(R.string.color)

        binding.executePendingBindings()
    }

    fun complete(){
        customer.color = binding.pickedColor?.color ?: customer.color
        onColorSelected?.invoke(customer)
        dismiss()
    }
}