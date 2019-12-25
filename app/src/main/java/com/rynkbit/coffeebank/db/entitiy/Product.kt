package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @PrimaryKey(autoGenerate = true) var uid: Long = 0,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "price") var price: Double = 0.0,
    @ColumnInfo(name = "stock") var stock: Int = 0
)