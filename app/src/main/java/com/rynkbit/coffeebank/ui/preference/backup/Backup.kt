package com.rynkbit.coffeebank.ui.preference.backup

import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.db.entitiy.Product
import com.rynkbit.coffeebank.db.entitiy.Transaction

class Backup(
    val customers: List<Customer> = listOf(),
    val products: List<Product> = listOf(),
    val transactions: List<Transaction> = listOf()
)