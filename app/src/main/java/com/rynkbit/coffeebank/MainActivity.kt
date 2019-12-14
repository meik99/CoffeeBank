package com.rynkbit.coffeebank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import com.rynkbit.coffeebank.logic.data.ProductFacade

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillDatabase()
    }

    private fun fillDatabase() {
        CustomerFacade(AppDatabase.getInstance(applicationContext))
            .createTestData()
        ProductFacade(AppDatabase.getInstance(applicationContext))
            .createTestData()
    }
}
