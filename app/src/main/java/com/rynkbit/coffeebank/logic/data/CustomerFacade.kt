package com.rynkbit.coffeebank.logic.data

import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class CustomerFacade(
    val appDatabase: AppDatabase
) {

    fun createTestData(){
        val customers = mutableListOf<Customer>()

        for(i in 1..3){
            customers.add(Customer(
                uid = 0,
                firstname = "Tester $i",
                lastname = "Testing $i",
                balance = i * 1.37
            ))
        }

        appDatabase
            .customerDao()
            .insertAll(*customers.toTypedArray())
            .subscribeOn(Schedulers.newThread())
            .subscribe()
    }

    fun getAll(limit: Int, offset: Int): Maybe<List<Customer>> {
        return appDatabase
            .customerDao()
            .getAll(limit, offset)
            .subscribeOn(Schedulers.newThread())
    }

    fun updateFun(): ((Customer) -> Unit) = { customer ->
        appDatabase
            .customerDao()
            .update(customer)
            .subscribeOn(Schedulers.newThread())
            .blockingGet()
    }

    fun delete(customer: Customer): Single<Unit> {
        return appDatabase
            .customerDao()
            .delete(customer)
            .subscribeOn(Schedulers.newThread())
    }

    fun insert(customer: Customer): Single<Unit> {
        return appDatabase
            .customerDao()
            .insert(customer)
            .subscribeOn(Schedulers.newThread())
    }
}