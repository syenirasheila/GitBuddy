package com.example.gitbuddy

import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gitbuddy.ui.main.MainActivity
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.contrib.RecyclerViewActions

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchUser(){
        onView(
            withId(R.id.sv_user)
        ).perform(click())

        onView(
            instanceOf(SearchView.SearchAutoComplete::class.java)
        ).perform(typeText("ben"))

        onView(withText("ben")).check(matches(isDisplayed()))
    }

    @Test
    fun switchTheme() {
        onView(withId(R.id.switchMode)).perform(click())
    }

    @Test
    fun clickFavoriteButton() {
        onView(withId(R.id.btn_favorite)).perform(click())
    }

    @Test
    fun clickUserCard_OpensDetailActivity() {
        onView(withId(R.id.rv_users))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.iv_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_repository)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_following)).check(matches(isDisplayed()))
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_name)).check(matches(withText("Tom Preston-Werner")))
        onView(withId(R.id.tv_username)).check(matches(withText("mojombo")))
        onView(withId(R.id.tv_followers)).check(matches(withText("23844")))
        onView(withId(R.id.tv_repository)).check(matches(withText("66")))
        onView(withId(R.id.tv_following)).check(matches(withText("11")))
    }
}