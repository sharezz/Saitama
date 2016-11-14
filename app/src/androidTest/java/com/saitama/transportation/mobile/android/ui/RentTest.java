package com.saitama.transportation.mobile.android.ui;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;

import com.saitama.transportation.mobile.android.MainActivity;
import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.TestConstants;
import com.saitama.transportation.mobile.android.ui.payment.PaymentActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by sharezzorama on 10/27/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void placeSelectTest() {
        onView(withId(R.id.email)).perform(typeText(TestConstants.TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(TestConstants.TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PaymentActivity.class.getName(), null, false);
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Adachi"));
        try {
            Thread.sleep(2000);
            marker.click();
            Thread.sleep(2000);
            onView(withId(R.id.rentButton)).perform(click());
            PaymentActivity paymentActivity = (PaymentActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 2000);
            onView(withId(R.id.cardName)).perform(typeText(TestConstants.TEST_CARD_NAME));
            onView(withId(R.id.cardNumber)).perform(typeText(TestConstants.TEST_CARD_NUMBER));
            onView(withId(R.id.cardValidationNumber)).perform(typeText(TestConstants.TEST_CARD_CODE), closeSoftKeyboard());
            onView(withId(R.id.orderButton)).perform(click());
            Thread.sleep(2000);
            assertTrue(paymentActivity.isFinishing());
        } catch (UiObjectNotFoundException e) {
            fail();
        } catch (InterruptedException e) {
            fail();
        }
    }
}
