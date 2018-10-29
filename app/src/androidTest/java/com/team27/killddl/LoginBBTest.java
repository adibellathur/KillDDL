package com.team27.killddl;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.doubleClick;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

/*
 * Espresso Black Box Testing for the Login Activity
 */
@RunWith(AndroidJUnit4.class)
public class LoginBBTest {
    @Rule
    public ActivityTestRule<LoginActivity> LoginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    /*
     * Check that the Login Activity pops up on app startup, and the Main Activity is not yet shown
     */
    @Test
    public void loginActivityShownAtStartup() {
        // Check that Login Activity is shown
        onView(withId(R.id.activity_login))
                .check(matches(isDisplayed()));

        // Check that Main Activity is not shown
        onView(withId(R.id.activity_main))
                .check(EspressoHelper.isNotDisplayed());
    }

    /*
     * Check that the user can press the "Skip" button on the login screen to redirect to the Main Activity
     */
    @Test
    public void btnSkipLogin() {
        // Click the "Skip" button in the Login Activity
        onView(withId(R.id.activity_login))
                .perform(click());

        // Check to see if Main Activity is now shown
        onView(withId(R.id.activity_main))
                .check(matches(isDisplayed()));
    }


    /*
     * Tests for Facebook Login to be written below
     */
}