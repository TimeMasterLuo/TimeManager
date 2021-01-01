package com.example.timemanager

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.timemanager.ui.dashboard.DashboardFragment
import com.example.timemanager.ui.home.Home
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import org.junit.Assert.*
import org.junit.Rule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.test.espresso.action.ViewActions.click
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityRule = ActivityTestRule(Home::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.timemanager", appContext.packageName)
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())
    }
}