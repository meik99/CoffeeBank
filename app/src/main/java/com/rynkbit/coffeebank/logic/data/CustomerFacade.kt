package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class CustomerFacade(
    private val appDatabase: AppDatabase
) {

    companion object {
        var customerTestDataCreated = false
    }

    fun createTestData(){
        if(!customerTestDataCreated) {
            val customers = mutableListOf<Customer>()

            for (i in 1..testDataAmount) {
                customers.add(
                    Customer(
                        uid = 0,
                        firstname = "Tester $i",
                        lastname = "Testing $i",
                        balance = i * 1.37
                    )
                )
            }

            appDatabase
                .customerDao()
                .insertAll(*customers.toTypedArray())
                .subscribeOn(Schedulers.newThread())
                .subscribe()

            customerTestDataCreated = true
        }
    }

    fun getAll(limit: Int, offset: Int): Maybe<List<Customer>> {
        return appDatabase
            .customerDao()
            .getAll(limit, offset)
            .subscribeOn(Schedulers.newThread())
    }

    fun delete(customer: Customer): Single<Unit> {
        return appDatabase
            .customerDao()
            .delete(customer)
            .subscribeOn(Schedulers.newThread())
    }

    fun insert(customer: Customer): Single<Long> {
        return appDatabase
            .customerDao()
            .insert(customer)
            .subscribeOn(Schedulers.newThread())
    }

    fun insertAll(customers: List<Customer>): Single<Unit> {
        return appDatabase
            .customerDao()
            .insertAll(*customers.toTypedArray())
            .subscribeOn(Schedulers.newThread())
    }

    fun deleteAll(): Single<Unit> {
        return appDatabase
            .customerDao()
            .deleteAll()
            .subscribeOn(Schedulers.newThread())
    }

    fun update(customer: Customer): Single<Unit> {
        return appDatabase
            .customerDao()
            .update(customer)
            .subscribeOn(Schedulers.newThread())
    }
}