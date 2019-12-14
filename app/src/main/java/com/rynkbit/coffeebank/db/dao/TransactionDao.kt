package com.rynkbit.coffeebank.db.dao

import androidx.room.*
import com.rynkbit.coffeebank.db.entitiy.Transaction
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction` LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): Maybe<List<Transaction>>

    @Query("SELECT * FROM `transaction` WHERE uid = :id")
    fun getById(id: Int): Single<Transaction>

    @Update
    fun update(transaction: Transaction): Single<Unit>

    @Insert
    fun insert(transaction: Transaction): Single<Unit>


    @Insert
    fun insertAll(vararg transaction: Transaction): Single<Unit>

    @Delete
    fun delete(transaction: Transaction): Single<Unit>
}