package com.mirza.nowmoneytest

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mirza.nowmoneytest.network.MyApi
import com.mirza.nowmoneytest.ui.login.LoginActivity
import com.mirza.nowmoneytest.ui.receivers.add.AddReceiverActivity
import com.mirza.nowmoneytest.util.snackbar
import kotlinx.android.synthetic.main.activity_add_receiver.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit

@RunWith(AndroidJUnit4ClassRunner::class)
class AddReceiverActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule<AddReceiverActivity>(AddReceiverActivity::class.java)

    @Test
    fun check_visibility_add_receiver_screen() {
        onView(withId(R.id.root_layout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_receiver_header_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_number))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_address))
            .check(matches(isDisplayed()))
        onView(withId(R.id.add_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun incorrect_data_check() {
        onView(withId(R.id.add_name)).perform(
            ViewActions.typeText("TestUI"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.add_number)).perform(
            ViewActions.typeText("1223"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.add_address)).perform(
            ViewActions.typeText("ssd"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.add_button)).perform(ViewActions.click())

        // Is toast displayed and is the message correct?
        onView(
            withText(LoginActivity.buildToastMessage("please check and enter correct values"))
        ).inRoot(
            ToastChecker()
        )
            .check(matches(isDisplayed()))

    }
}