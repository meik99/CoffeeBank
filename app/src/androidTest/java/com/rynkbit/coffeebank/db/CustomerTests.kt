package com.rynkbit.coffeebank.db

import android.content.Context
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.db.entitiy.Customer
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


/**
 * Since all entities use the same RxJava components
 * these tests would also succeed / fail for other entities
 */
@SmallTest
class CustomerTests {
    lateinit var appDatabase: AppDatabase
    lateinit var applicationContext: Context

    val customerAmount = 1000

    @Before
    fun init(){
        applicationContext = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(
            applicationContext,
            AppDatabase::class.java
        ).build()
    }

    @Test
    fun testInitDatabase(){
        applicationContext
        appDatabase
    }

    @Test
    fun testCreateCustomers(){
        createCustomers()

        val customerList = appDatabase
            .customerDao()
            .getAll(10000, 0)
            .subscribeOn(Schedulers.trampoline())
            .blockingGet()

        assertEquals(customerAmount, customerList.size)
    }

    @Test
    fun testUpdateCustomer(){
        createCustomers()

        val updatedFirstname = "Updated Firstname"
        val updatedLastname = "Updated Lastname"
        val updatedBalance = 101.50

        val customers = appDatabase
            .customerDao()
            .getAll(1, 0)
            .blockingGet()

        assertEquals(1, customers.size)

        val customer = customers[0]
        val updatingCustomer = Customer(
            uid = customer.uid,
            firstname = updatedFirstname,
            lastname = updatedLastname,
            balance = updatedBalance
        )

        appDatabase
            .customerDao()
            .update(updatingCustomer)
            .blockingGet()

        val updatedCustomer = appDatabase
            .customerDao()
            .getById(customer.uid)
            .blockingGet()

        assertEquals(updatingCustomer.uid, updatedCustomer.uid)
        assertEquals(updatingCustomer.firstname, updatedCustomer.firstname)
        assertEquals(updatingCustomer.lastname, updatedCustomer.lastname)
        assertEquals(updatingCustomer.balance, updatedCustomer.balance, 0.001)

        val allCustomers = appDatabase
            .customerDao()
            .getAll(10000, 0)
            .blockingGet()

        assertEquals(customerAmount, allCustomers.size)
    }

    @Test
    fun testDeleteCustomer(){
        createCustomers()

        appDatabase
            .customerDao()
            .delete(Customer(1))
            .blockingGet()

        val customers = appDatabase
            .customerDao()
            .getAll(10000, 0)
            .blockingGet()

        assertEquals(customerAmount-1, customers.size)
    }

    private fun createCustomers() {
        val customers = mutableListOf<Customer>()

        for (i in 1..customerAmount) {
            val customer = Customer(0, "Max $i", "Mustermann $i", i * 1.5)
            customers.add(customer)
        }

        appDatabase
            .customerDao()
            .insertAll(*customers.toTypedArray())
            .subscribeOn(Schedulers.trampoline())
            .blockingGet()
    }
}