package com.rynkbit.coffeebank.ui.preference.report

import android.content.Context
import android.net.Uri
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import com.rynkbit.coffeebank.logic.data.ProductFacade
import com.rynkbit.coffeebank.logic.data.TransactionFacade
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

class ReportWriter(val context: Context) {
    fun write(uri: Uri) {
        var transactions: List<Transaction> = listOf()
//        var products: List<Product> = listOf()
//        var customers: List<Customer> = listOf()


        TransactionFacade(AppDatabase.getInstance(context))
            .getAll(1000, 0)
            .map {
                transactions = it

                CustomerFacade(AppDatabase.getInstance(context))
                    .getAll(1000, 9)
                    .blockingGet()
            }
            .map {
//                customers = it

                ProductFacade(AppDatabase.getInstance(context))
                    .getAll(1000, 0)
                    .blockingGet()
            }
            .map {
//                products = it

                val stringBuilder = StringBuilder()

                stringBuilder
                    .append("Transaction ID")
                    .append(";")
                    .append("Date")
                    .append(";")
                    .append("Firstname")
                    .append(";")
                    .append("Lastname")
                    .append(";")
                    .append("Product")
                    .append(";")
                    .append("Price")
                    .append("\n")

                for (transaction in transactions) {
                    stringBuilder
                        .append(transaction.uid)
                        .append(";")
                        .append(
                            DateFormat
                                .getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                                .format(transaction.date)
                        )
                        .append(";")
                        .append(transaction.customerFirstname)
                        .append(";")
                        .append(transaction.customerLastname)
                        .append(";")
                        .append(transaction.productName)
                        .append(";")
                        .append(
                            NumberFormat
                                .getNumberInstance(Locale.getDefault())
                                .format(
                                    BigDecimal(transaction.productPrice ?: 0.0)
                                        .setScale(2, RoundingMode.HALF_UP)))
                        .append("\n")
                }

                context.contentResolver.openOutputStream(uri)?.let {output ->
                    val bufferedWriter = BufferedWriter(OutputStreamWriter(output))
                    bufferedWriter.write(stringBuilder.toString())
                    bufferedWriter.close()
                }
            }
            .subscribe()
    }
}