package com.app.shaditest

import android.view.View
import android.widget.RelativeLayout

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class ListUiTest {

    @get:Rule
    var mainActivityActivityTestRule = ActivityTestRule(
        MainActivity::class.java, true, true
    )

    @Test
    fun testRecycleVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivityActivityTestRule.activity.window.decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testForRecyclerClick() {
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivityActivityTestRule.activity.window.decorView)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

    }

    @Test
    fun testForRecyclerScroll() {

        val view = mainActivityActivityTestRule.activity.findViewById<RecyclerView>(R.id.list)
        val itemCount = view.childCount

        Espresso.onView(ViewMatchers.withId(R.id.list))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivityActivityTestRule.activity.window.decorView)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun testForRecyclerItemView() {

        Espresso.onView(ViewMatchers.withId(R.id.list))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivityActivityTestRule.activity.window.decorView)))
            .check(ViewAssertions.matches(withViewAtPosition(0, 0, ViewMatchers.withId(R.id.profile_image))))

    }

    private fun withViewAtPosition(position: Int, itemPosition: Int, item: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                item.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && item.matches(
                    (((viewHolder.itemView as RelativeLayout).getChildAt(0) as CardView)
                        .getChildAt(0) as RelativeLayout)
                        .getChildAt(itemPosition)
                )
            }
        }
    }
}
