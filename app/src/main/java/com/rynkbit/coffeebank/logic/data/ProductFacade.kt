package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Product
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ProductFacade(val appDatabase: AppDatabase) {
    companion object{
        var productTestDataCreated = false
    }

    fun createTestData() {
        if(!productTestDataCreated) {
            val products = mutableListOf<Product>()

            for (i in 1..3) {
                products.add(
                    Product(
                        uid = 0,
                        stock = i,
                        price = Math.random(),
                        name = "Product $i"
                    )
                )
            }

            appDatabase
                .productDao()
                .insertAll(*products.toTypedArray())
                .subscribeOn(Schedulers.newThread())
                .subscribe()

            productTestDataCreated = true
        }
    }

    fun getAll(limit: Int, offset: Int): Maybe<List<Product>> {
        return appDatabase
            .productDao()
            .getAll(limit, offset)
            .subscribeOn(Schedulers.newThread())
    }

    fun updateFun(): ((Product) -> Unit) = {
        appDatabase
            .productDao()
            .update(product = it)
            .subscribeOn(Schedulers.newThread())
            .blockingGet()
    }

    fun delete(product: Product): Single<Unit> {
        return appDatabase
            .productDao()
            .delete(product)
            .subscribeOn(Schedulers.newThread())
    }

    fun insert(product: Product): Single<Unit> {
        return appDatabase
            .productDao()
            .insert(product)
            .subscribeOn(Schedulers.newThread())
    }
}
