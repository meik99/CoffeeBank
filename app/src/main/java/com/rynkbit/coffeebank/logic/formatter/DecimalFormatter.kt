package com.rynkbit.coffeebank.logic.formatter

import java.math.BigDecimal
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

class DecimalFormatter {
    fun format(number: Double): String {
        val formatter = NumberFormat
            .getNumberInstance(Locale.getDefault())
        formatter.minimumFractionDigits = 2

        val decimal = BigDecimal(number)

        decimal.setScale(2, BigDecimal.ROUND_HALF_UP)

        return try {
            formatter.format(decimal)
        }catch (e: ParseException){
            String()
        }
    }

    fun parse(numberString: String): Double {
        val formttedNumberString = numberString.replace(",", ".")

        val formatter = NumberFormat
            .getNumberInstance(Locale.ENGLISH)

        return formatter.parse(formttedNumberString)?.toDouble() ?: 0.0
    }
}