package com.rynkbit.coffeebank.db.dao

import androidx.room.*
import com.rynkbit.coffeebank.db.entitiy.Customer
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): Maybe<List<Customer>>

    @Query("SELECT * FROM customer WHERE uid = :id")
    fun getById(id: Int): Single<Customer>

    @Update
    fun update(customer: Customer): Single<Unit>

    @Insert
    fun insert(customer: Customer): Single<Unit>


    @Insert
    fun insertAll(vararg customer: Customer): Single<Unit>

    @Delete
    fun delete(customer: Customer): Single<Unit>
}