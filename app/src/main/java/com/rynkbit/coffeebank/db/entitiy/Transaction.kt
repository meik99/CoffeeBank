package com.rynkbit.coffeebank.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Transaction (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "customer_id") val customer_id: Int,
    @ColumnInfo(name = "product_id") val product_id: Int,
    @ColumnInfo(name = "date") val date: Long
)