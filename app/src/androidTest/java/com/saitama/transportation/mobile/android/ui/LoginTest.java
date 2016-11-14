package com.saitama.transportation.mobile.android.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.saitama.transportation.mobile.android.MainActivity;
import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.SaitamaApplication;
import com.saitama.transportation.mobile.android.TestConstants;
import com.saitama.transportation.mobile.android.core.TokenKeeper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by sharezzorama on 10/27/16.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginTest {
    private TokenKeeper mTokenKeeper;

    @Before
    public void init() {
        mTokenKeeper = ((SaitamaApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).getCoreService().getTokenKeeper();
        mTokenKeeper.setToken("");
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void login() {
        onView(withId(R.id.email)).perform(typeText(TestConstants.TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(TestConstants.TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        try {
            onView(withId(R.id.mapView)).perform(click());
            mTokenKeeper.setToken("");
        } catch (NoMatchingViewException e) {
            fail();
        }
    }
}
