package com.rynkbit.coffeebank.ui.preference.backup

import android.content.Context
import android.net.Uri
import com.google.gson.GsonBuilder
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import com.rynkbit.coffeebank.logic.data.ProductFacade
import com.rynkbit.coffeebank.logic.data.TransactionFacade
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class BackupWriter(
    val context: Context
) {
    fun write(uri: Uri) {
        var gsonBackup: Backup
        var customers: List<Customer> = listOf()
        var products: List<Product> = listOf()
        var transactions: List<Transaction>

        CustomerFacade(AppDatabase.getInstance(context))
            .getAll(1000, 0)
            .map {
                customers = it

                ProductFacade(AppDatabase.getInstance(context))
                    .getAll(1000, 0)
                    .blockingGet()
            }
            .map {
                products = it

                TransactionFacade(AppDatabase.getInstance(context))
                    .getAll(1000, 0)
                    .blockingGet()
            }
            .map {
                transactions = it

                gsonBackup = Backup(
                    customers = customers,
                    products = products,
                    transactions = transactions
                )


                GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(gsonBackup)
            }
            .map {backup ->
                context.contentResolver?.openOutputStream(uri)?.let {
                    val bufferedWriter = BufferedWriter(OutputStreamWriter(it))
                    bufferedWriter.write(backup)
                    bufferedWriter.close()
                }
            }

            .subscribe()
    }
}