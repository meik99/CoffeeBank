package com.rynkbit.coffeebank.db.entitiy

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Customer (
    @PrimaryKey(autoGenerate = true) var uid: Long = 0,
    @ColumnInfo(name = "first_name") var firstname: String? = null,
    @ColumnInfo(name = "last_name") var lastname: String? = null,
    @ColumnInfo(name = "balance") var balance: Double = 0.0,
    @ColumnInfo(name = "color") var color: Int = Color.WHITE
)