package com.rynkbit.coffeebank.ui.preference.backup

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import com.rynkbit.coffeebank.logic.data.ProductFacade
import com.rynkbit.coffeebank.logic.data.TransactionFacade
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.IllegalStateException

class BackupReader (val context: Context) {
    fun read(uri: Uri): Boolean {
        context.contentResolver.openInputStream(uri)?.let {inputStream ->
            try {

                val reader = BufferedReader(InputStreamReader(inputStream))
                val backup = Gson()
                    .fromJson<Backup?>(reader, Backup::class.java)

                backup?.let {
                    TransactionFacade(AppDatabase.getInstance(context))
                        .deleteAll()
                        .blockingGet()

                    CustomerFacade(AppDatabase.getInstance(context))
                        .deleteAll()
                        .blockingGet()

                    ProductFacade(AppDatabase.getInstance(context))
                        .deleteAll()
                        .blockingGet()

                    CustomerFacade(AppDatabase.getInstance(context))
                        .insertAll(it.customers)
                        .blockingGet()

                    ProductFacade(AppDatabase.getInstance(context))
                        .insertAll(it.products)
                        .blockingGet()

                    TransactionFacade(AppDatabase.getInstance(context))
                        .insertAll(it.transactions)
                        .blockingGet()

                    return true
                }
            } catch (e: Exception){
            }
        }

        return false
    }
}