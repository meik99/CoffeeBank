package com.rynkbit.coffeebank.ui.customer

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.screenshot.ScreenCapture
import androidx.test.runner.screenshot.Screenshot
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
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

        onView(withId(R.id.listCustomer))
            .check { view, noViewFoundException ->
                assertNull(noViewFoundException)
                assertNotNull(view)
                assertTrue(view is RecyclerView)

                val recyclerView: RecyclerView = view as RecyclerView
                val adapter = recyclerView.adapter

                assertNotNull(adapter)
                assertEquals(1000, adapter?.itemCount)
            }

        onView(
            withId(R.id.listCustomer))
            .perform(
                actionOnItemAtPosition<CustomerAdapter.ViewHolder>(0, click()))
    }
}