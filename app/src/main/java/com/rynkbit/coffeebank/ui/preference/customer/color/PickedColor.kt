package com.rynkbit.coffeebank.ui.preference.customer.color

import android.graphics.Color
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableInt


data class PickedColor(
    var red: ObservableInt,
    var green: ObservableInt,
    var blue: ObservableInt
) : BaseObservable() {

    val color: Int
        get() = Color.rgb(red.get(), green.get(), blue.get())

    companion object {
        fun fromColor(color: Int) = PickedColor(
            red = ObservableInt(Color.red(color)),
            green = ObservableInt(Color.green(color)),
            blue = ObservableInt(Color.blue(color))
        )
    }
}