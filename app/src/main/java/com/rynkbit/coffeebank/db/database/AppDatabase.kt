package com.rynkbit.coffeebank.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rynkbit.coffeebank.db.dao.CustomerDao
import com.rynkbit.coffeebank.db.dao.ProductDao
import com.rynkbit.coffeebank.db.dao.TransactionDao
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction

@Database(entities = [Customer::class, Product::class, Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.inMemoryDatabaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java
//                    "coffeedb"
                )
                    .build()
            }

            return INSTANCE!!
        }
    }
}