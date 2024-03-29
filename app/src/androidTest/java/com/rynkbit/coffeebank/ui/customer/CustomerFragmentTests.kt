package com.rynkbit.coffeebank.ui.customer

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.db.database.AppDatabase
import com.rynkbit.coffeebank.logic.data.CustomerFacade
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@LargeTest
class CustomerFragmentTests {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testClickCustomer(){
        sleep(5000)

        val customers = CustomerFacade(AppDatabase.getInstance(activityRule.activity))
            .getAll(10000, 0)
            .blockingGet()

        onView(withId(R.id.listCustomer))
            .check { view, noViewFoundException ->
                assertNull(noViewFoundException)
                assertNotNull(view)
                assertTrue(view is RecyclerView)

                val recyclerView: RecyclerView = view as RecyclerView
                val adapter = recyclerView.adapter

                assertNotNull(adapter)
                assertEquals(customers.size, adapter?.itemCount)
            }

        onView(
            withId(R.id.listCustomer))
            .perform(
                actionOnItemAtPosition<CustomerAdapter.ViewHolder>(0, click()))
    }
}