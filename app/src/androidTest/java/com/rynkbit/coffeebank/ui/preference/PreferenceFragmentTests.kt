package com.rynkbit.coffeebank.ui.preference

import androidx.preference.Preference
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.PreferenceMatchers
import androidx.test.espresso.matcher.PreferenceMatchers.withKey
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rynkbit.coffeebank.MainActivity
import com.rynkbit.coffeebank.R
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep


@LargeTest
class PreferenceFragmentTests {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testOpenFragment(){
        sleep(500)

        Espresso.onView(withId(R.id.fabSettings))
            .perform(click())
    }

    @Test
    fun testSetAdminPassword(){

        sleep(2000)

        Espresso.onView(withId(R.id.fabSettings))
            .perform(click())

        sleep(500)

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItem<androidx.preference.PreferenceViewHolder>(
                    hasDescendant(withText(R.string.set_admin_password)),
                click()))

        sleep(500)


    }
}