package com.rynkbit.coffeebank.ui.preference.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import java.lang.IllegalArgumentException

class CRUDCustomerViewmodelFactoy(private val customerFacade: CustomerFacade) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CRUDCustomerViewmodel::class.java)){
            return CRUDCustomerViewmodel(customerFacade) as T
        }

        throw IllegalArgumentException("Class must be of type " +
                CRUDCustomerViewmodel::class.java.name)
    }

}