package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Transaction (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "customer_id") val customerId: Long = 0,
    @ColumnInfo(name = "customer_firstname") val customerFirstname: String? = null,
    @ColumnInfo(name = "customer_lastname") val customerLastname: String? = null,
    @ColumnInfo(name = "product_id") val productId: Long = 0,
    @ColumnInfo(name = "product_name") val productName: String? = null,
    @ColumnInfo(name = "product_price") val productPrice: Double? = null,
    @ColumnInfo(name = "date") val date: Long? = null
)