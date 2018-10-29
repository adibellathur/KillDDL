package com.team27.killddl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.res.Resources;
import android.widget.TextView;

import java.util.Calendar;


import android.support.test.espresso.contrib.PickerActions;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
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
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

/*
 * Espresso Black Box Testing for Navigation in the Main Activity
 */
@RunWith(AndroidJUnit4.class)
public class NavigationBBTest {
    @Rule
    public ActivityTestRule<MainActivity> MainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    // String representing today's date in MM/dd/yyyy format
    String dateStr;

    /*
     * Ensure we begin at the default screen (Calendar-Daily)
     */
    @Before
    public void init() {
        MainActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        dateStr = EspressoHelper.getDateString();
    }

    /*
     * Check that Calendar-Today is shown by default
     */
    @Test
    public void calendarTodayDefault() {
        onView(withId(R.id.dateDisplay))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that Calendar-Today is showing tasks from Today's date (not any other day)
     */
    @Test
    public void calendarTodayDateIsToday() {
        // Check to see if dateDisplay is showing Today's date
        onView(withId(R.id.dateDisplay))
                .check(matches(withText(containsString(dateStr))));
    }

    /*
     * Check that swiping left on Calendar-Today will bring you to Calendar-Month
     * - Swiping right on Calendar-Month will be tested later on
     */
    @Test
    public void calendarSwipeToMonthView() {
        // Check that Calendar-Month is not shown yet
        onView(withId(R.id.monthDateDisplay))
                .check(EspressoHelper.isNotDisplayed());

        // Swipe left
        onView(withId(R.id.calendar_viewpager))
                .perform(swipeLeft());

        // Check that Calendar-Month is shown after the swipe
        onView(withId(R.id.monthDateDisplay))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that Calendar-Month by default is showing Today's date
     */
    @Test
    public void calendarMonthDateIsToday() {
        // Check to see if monthDateDisplay is showing Today's date
        onView(withId(R.id.monthDateDisplay))
                .check(matches(withText(containsString(dateStr))));
    }

    /*
     * Check that Calendar-Month date picker is functional
     */
    @Test
    public void calendarMonthDatePicker() {
        // Change date
        /* - get this working
        onView(allOf(withId(R.id.monthCalendarView),
                withParent(withId(R.id.monthCalendarView))))
                .perform(PickerActions.setDate(2016, 5, 16));]
        // Check to see if new date is reflected
        onView(withId(R.id.monthDateDisplay))
                .check(matches(withText(containsString("05/16/2016"))));
        */
    }

    /*
     * Check that swiping right on Calendar-Month will bring you to Calendar-Today
     */
    @Test
    public void calendarSwipeToTodayView() {
        // Swipe right
        onView(withId(R.id.calendar_viewpager))
                .perform(swipeRight());

        // Check that Calendar-Month is shown after the swipe
        onView(withId(R.id.dateDisplay))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that the FAB opens the Create Task Activity
     */
    @Test
    public void FABCreateTask() {
        // Click on "+" FAB
        onView(withId(R.id.fab_add))
                .perform(click());
        // Check to see if Create Task Activity is open
        onView(withId(R.id.activity_create_task))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that we can use the bottom navigation to go to the Notifications screen
     */
    @Test
    public void notificationsBottomNav() {
        // Click on "Notifications" tab in the bottom navigation
        onView(withId(R.id.action_notification))
                .perform(click());
        // Check to see if "Notifications" fragment is open
        onView(withId(R.id.fragment_notifications))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that we can use the bottom navigation to go to the Settings screen
     */
    @Test
    public void settingsBottomNav() {
        // Click on "Settings" tab in the bottom navigation
        onView(withId(R.id.action_settings))
                .perform(click());
        // Check to see if "Settings" fragment is open
        onView(withId(R.id.fragment_settings))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that the Login/Logout button sends user back to Login screen
     */
    @Test
    public void settingsExitToLogin() {
        // Click on "Settings" tab in the bottom navigation
        onView(withId(R.id.action_settings))
                .perform(click());
        // Check to see if Login/Logout button is present
        onView(withId(R.id.loginLogout))
                .check(matches(isDisplayed()));
        // Click on Login/Logout button
        onView(withId(R.id.loginLogout))
                .perform(click());
        // Check that Login Activity is shown
        onView(withId(R.id.activity_login))
                .check(matches(isDisplayed()));
    }

    /*
     * Check that we can use the bottom navigation to go to the Calendar screen
     * - Since calendar screen is default, we first go to the Notification screen
     * - Assume this test works IF AND ONLY IF @Test notificationsBottomNav works
     */
    @Test
    public void calendarBottomNav() {
        // Click on "Notifications" tab in the bottom navigation
        onView(withId(R.id.action_notification))
                .perform(click());
        // Click on "Notifications" tab in the bottom navigation
        onView(withId(R.id.action_calendar))
                .perform(click());
        // Check to see if "Calendar" fragment is open
        onView(withId(R.id.fragment_calendar))
                .check(matches(isDisplayed()));
    }

}