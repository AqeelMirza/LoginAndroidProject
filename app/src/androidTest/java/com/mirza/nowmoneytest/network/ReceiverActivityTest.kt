package com.mirza.nowmoneytest.network

import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.mirza.nowmoneytest.R
import com.mirza.nowmoneytest.ui.receivers.ReceiverActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ReceiverActivityTest {

    private var auth: String? = null
    private lateinit var mockServer: MockWebServer

    @JvmField
    @Rule
    var activityRule = ActivityTestRule(ReceiverActivity::class.java, false, false)

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start(8080)
    }

    @After
    fun tearDown() = mockServer.shutdown()

    @Test
    fun successCase() {
        mockServer.dispatcher = MockServer.ResponseDispatcher()
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ReceiverActivity::class.java
        )
        activityRule.launchActivity(intent)

        SystemClock.sleep(2000)
        //  not visible on success response
        onView(ViewMatchers.withId(R.id.receiver_no_item_text))
            .check(matches(Matchers.not(ViewMatchers.isDisplayed())))

        onView(ViewMatchers.withId(R.id.receiver_recyclerView)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(activityRule.getActivity().getWindow().getDecorView())
            )
        )
            .check(
                matches(
                    Matchers.not(ViewMatchers.isDisplayed())
                )
            )
    }


    @Test
    fun failureCase() {
        mockServer.dispatcher = MockServer.ErrorDispatcher()
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            ReceiverActivity::class.java
        )
        activityRule.launchActivity(intent)
        //  failure layout visible
        onView(ViewMatchers.withId(R.id.receiver_no_item_text))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testName() {
        val i = Intent()
        i.putExtra("auth", auth)
        activityRule.launchActivity(i)

        //check if no auth
        check_visibility_receiver_screen()
    }

    fun check_visibility_receiver_screen() {
        onView(ViewMatchers.withId(R.id.parent_layout))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.toolbar))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.include_activity_receiver))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}