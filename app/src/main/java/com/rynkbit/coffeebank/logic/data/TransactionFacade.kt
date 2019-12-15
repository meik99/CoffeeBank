package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction
import io.reactivex.Maybe
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
            customerId = updatedCustomer.uid,
            productId = updatedProduct.uid,
            customerFirstname = customer.firstname,
            customerLastname = customer.lastname,
            productName = product.name,
            productPrice = product.price,
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

    fun getAll(limit: Int, offset: Int): Maybe<List<Transaction>> {
        return appDatabase
            .transactionDao()
            .getAll(limit, offset)
            .subscribeOn(Schedulers.newThread())
    }

    fun delete(transaction: Transaction): Single<Unit> {
        return appDatabase
            .productDao()
            .getById(transaction.productId)
            .subscribeOn(Schedulers.newThread())
            .map {product ->
                appDatabase
                    .productDao()
                    .update(
                        Product(
                            uid = product.uid,
                            name = product.name,
                            stock = product.stock+1,
                            price = product.price
                    ))
                    .blockingGet()
            }
            .map {
                appDatabase
                    .customerDao()
                    .getById(transaction.customerId)
                    .blockingGet()
            }
            .map {  customer ->
                appDatabase
                    .customerDao()
                    .update(
                        Customer(
                            uid = customer.uid,
                            firstname = customer.firstname,
                            lastname = customer.lastname,
                            balance = customer.balance - (transaction.productPrice ?: 0.0)
                    ))
                    .blockingGet()
            }
            .map {
                appDatabase
                    .transactionDao()
                    .delete(transaction)
                    .blockingGet()
            }
    }

    fun insertAll(transactions: List<Transaction>): Single<Unit> {
        return appDatabase
            .transactionDao()
            .insertAll(*transactions.toTypedArray())
            .subscribeOn(Schedulers.newThread())
    }

    fun deleteAll(): Single<Unit> {
        return appDatabase
            .transactionDao()
            .deleteAll()
            .subscribeOn(Schedulers.newThread())
    }
}