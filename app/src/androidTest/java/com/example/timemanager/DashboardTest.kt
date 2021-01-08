package com.example.timemanager

import android.widget.Button
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.timemanager.ui.home.Home
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DashboardTest {
    @get:Rule
    val activityRule = ActivityTestRule(Home::class.java)
    @Test
    fun commonRecordTest() {
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).perform(click())
        onView(withId(R.id.typeText)).check(matches(withText("专注模式")))
        onView(withId(R.id.coins)).check(matches(withText("8金币")))
    }
    @Test
    fun loginTest() {
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2020-11-17 15.45"))))).check(doesNotExist())
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_proflie)).perform(click())
        onView(withId(R.id.login_button)).check(matches(isAssignableFrom(Button::class.java))).perform(click())
        onView(withId(R.id.username)).check(matches(isAssignableFrom(EditText::class.java))).perform(typeText("shaw111"))
        onView(withId(R.id.password)).check(matches(isAssignableFrom(EditText::class.java))).perform(typeText("123"))
        onView(withId(R.id.login)).check(matches(isAssignableFrom(Button::class.java))).perform(click())
    }
    @Test
    fun clockTest(){
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).perform(click())
        onView(withId(R.id.typeText)).check(matches(withText("自设闹钟")))
        onView(withId(R.id.coins)).check(matches(withText("10金币")))
    }
    @Test
    fun analyzeTest() {
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())

        onView(withId(R.id.analyze_button)).check(matches(isAssignableFrom(FloatingActionButton::class.java))).perform(click())

        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(not(isDisplayed())))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches((isDisplayed())))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches((isDisplayed())))

        onView(withText("周")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))

        onView(withText("总览")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(not(isDisplayed())))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))

        onView(withText("年")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))

        onView(withText("总览")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(not(isDisplayed())))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))

        onView(withText("月")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))

        onView(withText("总览")).perform(click())
        onView(withId(R.id.aa_chart_view)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(not(isDisplayed())))
        onView(withId(R.id.aa_chart_view1)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.aa_chart_view2)).check(matches(isAssignableFrom(AAChartView::class.java))).check(matches(isDisplayed()))
    }

    @Test
    fun dashboardTest(){
        onView(withId(R.id.nav_view)).check(matches(isAssignableFrom(BottomNavigationView::class.java)))
        onView(withId(R.id.navigation_dashboard)).perform(click())

        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).check(matches(isAssignableFrom(Button::class.java)))
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).check(matches(isAssignableFrom(Button::class.java)))

        onView(withText("自设闹钟")).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).check(matches(isAssignableFrom(Button::class.java)))
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).check(doesNotExist())

        onView(withText("远离手机")).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).check(matches(isAssignableFrom(Button::class.java)))
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).check(doesNotExist())

        onView(withText("好友闹钟")).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).check(doesNotExist())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).check(doesNotExist())

        onView(withText("全部")).perform(click())
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-07 15:29"))))).check(matches(isAssignableFrom(Button::class.java)))
        onView(allOf(withText("查看详情 >"), hasSibling(withChild(withText("2021-01-08 00:24"))))).check(matches(isAssignableFrom(Button::class.java)))
    }
}