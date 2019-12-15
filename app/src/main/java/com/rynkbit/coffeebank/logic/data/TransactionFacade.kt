package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TransactionFacade(val appDatabase: AppDatabase) {
    fun buy(customer: Customer, product: Product): Single<Disposable> {
        val updatedCustomer = Customer(
            uid = customer.uid,
            firstname = customer.firstname,
            lastname = customer.lastname,
            balance = customer.balance + product.price
        )
        val updatedProduct = Product(
            uid = product.uid,
            name = product.name,
            stock = product.stock-1,
            price = product.price
        )
        val transaction = Transaction(
            uid = 0,
            customer_id = updatedCustomer.uid,
            product_id = updatedProduct.uid,
            date = Date().time
        )


        return appDatabase
            .customerDao()
            .update(updatedCustomer)
            .subscribeOn(Schedulers.newThread())
            .map {
                appDatabase
                    .productDao()
                    .update(updatedProduct)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe()
            }
            .map {
                appDatabase
                    .transactionDao()
                    .insert(transaction)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe()
            }
    }
}