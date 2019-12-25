package com.rynkbit.coffeebank.ui.preference.customer.color

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.entitiy.Customer
import kotlinx.android.synthetic.main.dialog_color_picker.*

class ColorPickerDialog(
    context: Context,
    val customer: Customer
) : AlertDialog(context), SeekBar.OnSeekBarChangeListener {
    private val red = MutableLiveData<Int>()
    private val green = MutableLiveData<Int>()
    private val blue = MutableLiveData<Int>()
    private val color
        get() = Color.rgb(red.value!!, green.value!!, blue.value!!)

    private lateinit var seekerObserver: Observer<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customerRed = Color.red(customer.color)
        val customerGreen = Color.green(customer.color)
        val customerBlue = Color.blue(customer.color)

        setContentView(R.layout.dialog_color_picker)
        setTitle(R.string.color)

        seekRed.setOnSeekBarChangeListener(this)
        seekGreen.setOnSeekBarChangeListener(this)
        seekBlue.setOnSeekBarChangeListener(this)

        seekerObserver = Observer {
            txtColorPreview.setBackgroundColor(color)
        }

        red.value = customerRed
        green.value = customerGreen
        blue.value = customerBlue

        seekRed.progress = customerRed
        seekGreen.progress = customerGreen
        seekBlue.progress = customerBlue

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            customer.color = color
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()

        red.observeForever(seekerObserver)
        green.observeForever(seekerObserver)
        blue.observeForever(seekerObserver)
    }

    override fun onStop() {
        super.onStop()

        red.removeObserver(seekerObserver)
        green.removeObserver(seekerObserver)
        blue.removeObserver(seekerObserver)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when(seekBar) {
            seekRed -> red.postValue(progress)
            seekGreen -> green.postValue(progress)
            seekBlue -> blue.postValue(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }
    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
}