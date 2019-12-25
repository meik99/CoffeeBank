package com.rynkbit.coffeebank.ui.customer

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.preference.PreferenceManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
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
    private lateinit var context: Context

    @Before
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

    private fun isKeyboardShown(): Boolean {
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