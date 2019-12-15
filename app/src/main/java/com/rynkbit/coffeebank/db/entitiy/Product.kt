package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "price") val price: Double = 0.0,
    @ColumnInfo(name = "stock") val stock: Int = 0
)