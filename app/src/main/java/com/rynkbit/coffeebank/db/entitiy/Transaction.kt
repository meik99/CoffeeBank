package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transaction (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "customer_id") val customerId: Int,
    @ColumnInfo(name = "customer_firstname") val customerFirstname: String?,
    @ColumnInfo(name = "customer_lastname") val customerLastname: String?,
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "product_price") val productPrice: Double,
    @ColumnInfo(name = "date") val date: Long
)