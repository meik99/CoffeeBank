package com.rynkbit.coffeebank.ui.preference.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import io.reactivex.disposables.CompositeDisposable

class CRUDCustomerViewmodel(private val customerFacade: CustomerFacade) : ViewModel() {

    val customers: LiveData<List<Customer>>
        get() = _customers

    private val _customers = MutableLiveData<List<Customer>>()
    private val _disposables = CompositeDisposable()

    init {
        _disposables.add(
            customerFacade
                .getAll(2000, 0)
                .subscribe {
                    _customers.postValue(it)
                })
    }

    fun deleteCustomer(customer: Customer) {
        _disposables.add(
            customerFacade
                .delete(customer)
                .subscribe { _, _ ->
                    _customers.postValue(
                        _customers.value?.filter {  it.uid != customer.uid }
                    )
                })
    }

    fun addCustomer(customer: Customer) {
        _disposables.add(
            customerFacade
                .insert(customer)
                .subscribe { id, _ ->
                    customer.uid = id
                    val list = ArrayList<Customer>(2000)
                    list.addAll(_customers.value!!)
                    list.add(customer)

                    _customers.postValue(list)
                }
        )
    }

    fun updateCustomer(customer: Customer) {
        _disposables.add(
            customerFacade
                .update(customer)
                .subscribe { _, _ ->

                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        _disposables.dispose()
    }
}