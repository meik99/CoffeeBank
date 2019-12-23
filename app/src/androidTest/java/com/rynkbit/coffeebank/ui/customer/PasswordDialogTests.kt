package com.rynkbit.coffeebank.ui.customer

import android.content.Context
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager
import androidx.preference.PreferenceManager
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
import kotlinx.android.synthetic.main.fragment_customer.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@LargeTest
class PasswordDialogTests{
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    lateinit var context: Context

    @Before()
    fun setSharedPreferences(){
        context = activityRule.activity.applicationContext
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .clear()
            .putString("password", "abc")
            .commit()

    }

    @Test
    fun testPasswordDialog(){
        sleep(200)

        onView(withId(R.id.fabSettings))
            .perform(click())

        sleep(200)

        assertTrue(isKeyboardShown())

        sleep(200)
    }

    fun isKeyboardShown(): Boolean {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    @After
    fun clearSharedPreferences(){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .clear()
            .commit()
    }
}