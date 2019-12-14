package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Product
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class ProductFacade(val appDatabase: AppDatabase) {
    fun createTestData() {
        val products = mutableListOf<Product>()

        for (i in 1..10000) {
            products.add(
                Product(
                    uid = 0,
                    stock = i,
                    price = Math.random(),
                    name = "Product $i"))
        }

        appDatabase
            .productDao()
            .insertAll(*products.toTypedArray())
            .subscribeOn(Schedulers.newThread())
            .subscribe()
    }

    fun getAll(limit: Int, offset: Int): Maybe<List<Product>> {
        return appDatabase
            .productDao()
            .getAll(limit, offset)
            .subscribeOn(Schedulers.newThread())
    }
}
