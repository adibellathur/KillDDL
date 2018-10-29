package com.team27.killddl;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
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
import static android.support.test.espresso.action.ViewActions.replaceText;
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
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;


/*
 * Espresso Black Box Testing for Task Details
 * - Assumes that CreateTaskBBTest runs without error
 */
@RunWith(AndroidJUnit4.class)
public class TaskDetailBBTest {
    @Rule
    public ActivityTestRule<MainActivity> MainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    // String representing today's date in MM/dd/yyyy format
    String dateStr;

    /*
     * Ensure we begin at the default screen (Calendar-Today)
     * - Create an example Task to make sure at least one task exists
     */
    @Before
    public void init(){
        MainActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        dateStr = EspressoHelper.getDateString();

        // Open Create Task page before
        EspressoHelper.openCreateTask();

        // Create Example Task
        /*
         * Name: testTask_[todaysdate]
         * Description: Example description
         * Priority: 2
         */
        String name = "testTask_" + dateStr;
        String desc = "Example description";
        EspressoHelper.insertTextIntoInput(R.id.taskName, name);
        EspressoHelper.insertTextIntoInput(R.id.taskDescription, desc);
        onView(withId(R.id.color_orange))
                .perform(scrollTo(), click());
        onView(withId(R.id.btnSubmit))
                .perform(scrollTo(), click());
    }

    /*
     * Check to see that clicking on a Task will open the Task View Activity
     */
    @Test
    public void openTaskView() {
        EspressoHelper.openFirstInTaskList();
        // Check to see if Task View Activity opens up
        onView(withId(R.id.textView_taskName))
                .check(matches(isDisplayed()));
    }

    /*
     * Check to see that clicking the "Edit" button will open the Edit Task Activity
     */
    @Test
    public void taskViewBtnEdit() {
        EspressoHelper.openFirstInTaskList();
        // Check to see if Task View Edit button is present, then click
        onView(withId(R.id.edit_button))
                .check(matches(isDisplayed()))
                .perform(click());
        // Check to see that Task Edit Activity is open
        onView(withId(R.id.taskName))
                .check(matches(isDisplayed()));
    }

    /*
     * Check to see that clicking the "Delete" button is functional and redirects to the last activity
     */
    @Test
    public void taskViewBtnDelete() {
        EspressoHelper.openFirstInTaskList();
        // Check to see if Task View Edit button is present, then click
        onView(withId(R.id.button_delete))
                .check(matches(isDisplayed()))
                .perform(click());
        // Check to see that user is redirected to Calendar-Today
        onView(withId(R.id.fragment_calendar))
                .check(matches(isDisplayed()));
    }

}