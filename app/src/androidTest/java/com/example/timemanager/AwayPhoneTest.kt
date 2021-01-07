package com.example.timemanager

import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.timemanager.ui.home.Home
import com.google.android.material.bottomnavigation.BottomNavigationView
import mehdi.sakout.fancybuttons.FancyButton
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AwayPhoneTest {
    @get:Rule
    val activityRule = ActivityTestRule(Home::class.java)

    @Test
    fun loginTest() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(BottomNavigationView::class.java)))
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(BottomNavigationView::class.java)))
        Espresso.onView(ViewMatchers.withId(R.id.navigation_proflie)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(
            ViewActions.click()
        )
        Espresso.onView(ViewMatchers.withId(R.id.username))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(EditText::class.java))).perform(
            ViewActions.typeText("shaw")
        )
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(EditText::class.java))).perform(
            ViewActions.typeText("123")
        )
        Espresso.onView(ViewMatchers.withId(R.id.login))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(
            ViewActions.click()
        )
    }

    @Test
    fun accessPermissions() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(BottomNavigationView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.away_phone_button))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.no))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.away_phone_button))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.yes))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())
//
//        Espresso.onView(ViewMatchers.withId(R.id.stopAwayPhone))
//            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(FancyButton::class.java))).perform(ViewActions.click())
//
//        Espresso.onView(ViewMatchers.withId(R.id.close))
//            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())
    }

    @Test
    fun awayPhoneNormal() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(BottomNavigationView::class.java)))

        Espresso.onView(ViewMatchers.withId(R.id.away_phone_button))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.normal))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(ImageView::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.startAwayPhone))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(FancyButton::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.stopAwayPhone))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(FancyButton::class.java))).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.close))
            .check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(Button::class.java))).perform(ViewActions.click())
    }
}