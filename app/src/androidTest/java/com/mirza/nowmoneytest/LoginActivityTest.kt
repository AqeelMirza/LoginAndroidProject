package com.mirza.nowmoneytest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mirza.nowmoneytest.ui.login.LoginActivity
import com.mirza.nowmoneytest.ui.login.LoginActivity.Companion.buildToastMessage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun check_visibility_login_screen() {
        onView(withId(R.id.root_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_text_username)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_text_password)).check(matches(isDisplayed()))
        onView(withId(R.id.button_sign_in)).check(matches(isDisplayed()))
    }

    @Test
    fun input_correct_values_login() {
        onView(withId(R.id.edit_text_username)).perform(typeText("aqeel"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText("AqeeL123"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())

        // Is toast displayed and is the message correct?
        onView(
            withText(buildToastMessage("success"))
        ).inRoot(
            ToastChecker()
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun input_incorrect_values_login() {
        onView(withId(R.id.edit_text_username)).perform(typeText("aqeel"), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())

        // Is toast displayed and is the message correct?
        onView(
            withText(buildToastMessage("failed"))
        ).inRoot(
            ToastChecker()
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun input_empty_values_login() {
        onView(withId(R.id.edit_text_username)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.edit_text_password)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.button_sign_in)).perform(click())

        // Is toast displayed and is the message correct?
        onView(withText(buildToastMessage("please check and enter correct values"))).inRoot(
            ToastChecker()
        )
            .check(matches(isDisplayed()))
    }

}