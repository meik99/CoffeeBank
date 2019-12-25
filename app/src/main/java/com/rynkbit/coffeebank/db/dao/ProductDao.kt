package com.rynkbit.coffeebank.db.dao

import androidx.room.*
import com.rynkbit.coffeebank.db.entitiy.Product
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ProductDao{
    @Query("SELECT * FROM product LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): Maybe<List<Product>>

    @Query("SELECT * FROM product WHERE uid = :id")
    fun getById(id: Long): Single<Product>

    @Update
    fun update(product: Product): Single<Unit>

    @Insert
    fun insert(product: Product): Single<Unit>


    @Insert
    fun insertAll(vararg product: Product): Single<Unit>

    @Delete
    fun delete(product: Product): Single<Unit>

    @Query("DELETE FROM product")
    fun deleteAll(): Single<Unit>
}