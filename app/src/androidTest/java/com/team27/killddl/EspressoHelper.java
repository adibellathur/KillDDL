package com.team27.killddl;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.util.HumanReadables;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.view.View;

import java.util.Calendar;

/*
 * Class with helper functions for Espresso tests, written if necessary
 * - NOTE: use of these helper functions assume that specified Views are in display!
 */
public class EspressoHelper {

    /*
     * Checks that a view is not visible
     * - View may or may not still exist in the hierarchy
     */
    public static ViewAssertion isNotDisplayed() {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noView) {
                if (view != null && isDisplayed().matches(view)) {
                    throw new AssertionError("View is present in the hierarchy and Displayed: "
                            + HumanReadables.describe(view));
                }
            }
        };
    }

    /*
     * Helper function opens Create Task menu
     */
    public static void openCreateTask() {
        // Click on "+" FAB
        onView(withId(R.id.fab_add))
                .perform(click());
    }

    /*
     * Helper function to insert text into form field
     */
    public static void insertTextIntoInput(Integer inputId, String text) {
        onView(withId(inputId)).perform(scrollTo(), typeText(text), closeSoftKeyboard());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * Helper function to select dropdown option
     */
    public static void selectDropdownOption(Integer inputId, String option) {
        onView(withId(inputId))
                .perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is(option)))
                .perform(click());
    }

    /*
     * Helper function to click on the first Task in the TaskList
     */
    public static void openFirstInTaskList() {
        onData(anything()).inAdapterView(withId(R.id.taskList)).atPosition(0)
                .perform(click());
    }

    /*
     * Helper function to build a string out of today's date
     */
    public static String getDateString() {
        Calendar today = Calendar.getInstance();
        int day, month, year;
        day = today.get(Calendar.DAY_OF_MONTH);
        month = today.get(Calendar.MONTH);
        year = today.get(Calendar.YEAR);
        String dateStr = "" + (month+1) + "/" + day + "/" + year;
        return dateStr;
    }

}
