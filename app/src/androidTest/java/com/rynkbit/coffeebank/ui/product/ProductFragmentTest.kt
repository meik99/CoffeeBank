package com.rynkbit.coffeebank.ui.product

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.ui.customer.CustomerAdapter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@LargeTest
class ProductFragmentTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testClickProduct(){
        sleep(5000)

        Espresso.onView(ViewMatchers.withId(R.id.listCustomer))
            .check { view, noViewFoundException ->
                Assert.assertNull(noViewFoundException)
                Assert.assertNotNull(view)
                Assert.assertTrue(view is RecyclerView)

                val recyclerView: RecyclerView = view as RecyclerView
                val adapter = recyclerView.adapter

                Assert.assertNotNull(adapter)
                Assert.assertEquals(1000, adapter?.itemCount)
            }

        Espresso.onView(ViewMatchers.withId(R.id.listCustomer))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CustomerAdapter.ViewHolder>(
                    0, ViewActions.click()
                ))

        sleep(2)

        Espresso.onView(ViewMatchers.withId(R.id.listProducts))
            .check { view, noViewFoundException ->
                Assert.assertNull(noViewFoundException)
                Assert.assertNotNull(view)
                Assert.assertTrue(view is RecyclerView)

                val recyclerView: RecyclerView = view as RecyclerView
                val adapter = recyclerView.adapter

                Assert.assertNotNull(adapter)
                Assert.assertEquals(1000, adapter?.itemCount)
            }
    }
}