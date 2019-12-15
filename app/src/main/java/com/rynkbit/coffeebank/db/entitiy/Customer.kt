package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Customer (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "first_name") val firstname: String? = null,
    @ColumnInfo(name = "last_name") val lastname: String? = null,
    @ColumnInfo(name = "balance") val balance: Double = 0.0
)