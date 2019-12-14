package com.rynkbit.coffeebank.ui

import androidx.lifecycle.ViewModel
import com.rynkbit.coffeebank.db.entitiy.Customer

class CustomerProductViewModel: ViewModel() {
    var customer: Customer? = null
}