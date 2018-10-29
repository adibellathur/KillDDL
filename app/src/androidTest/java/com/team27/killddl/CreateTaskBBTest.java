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
 * Espresso Black Box Testing for Creating Task Activity
 */
@RunWith(AndroidJUnit4.class)
public class CreateTaskBBTest {
    @Rule
    public ActivityTestRule<MainActivity> MainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    // String representing today's date in MM/dd/yyyy format
    String dateStr;

    /*
     * Ensure we begin at the default screen (Calendar-Daily)
     * - Start at default screen to prevent errors after submit
     */
    @Before
    public void init(){
        MainActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        dateStr = EspressoHelper.getDateString();

        // Open Create Task Activity before each test
        EspressoHelper.openCreateTask();
    }

    /*
     * Check that user can type text into Name field
     */
    @Test
    public void typeNameField() {
        String name = "testTask_" + dateStr;
        EspressoHelper.insertTextIntoInput(R.id.taskName, name);
        onView(withId(R.id.taskName))
                .check(matches(withText(name)));
    }

    /*
     * Check that user can type text into Description field
     */
    @Test
    public void typeDescriptionField() {
        String desc = "testTask_description";
        EspressoHelper.insertTextIntoInput(R.id.taskDescription, desc);
        onView(withId(R.id.taskDescription))
                .check(matches(withText(desc)));
    }

    /*
     * Check that user can type into NumRecurrence field
     */
    @Test
    public void typeNumRecurrenceField() {
        String num = "4";
        onView(withId(R.id.numRecurrences))
                .perform(scrollTo(), typeText(String.valueOf(num)));
        onView(withId(R.id.numRecurrences))
                .check(matches(withText(num)));
    }

    /*
     * Check that all radio buttons are clickable
     */
    @Test
    public void selectRadioButtons() {
        /* Click each Radio button */
        onView(withId(R.id.color_green))
                .perform(scrollTo(), click());
        onView(withId(R.id.color_yellow))
                .perform(scrollTo(), click());
        onView(withId(R.id.color_orange))
                .perform(scrollTo(), click());
        onView(withId(R.id.color_red))
                .perform(scrollTo(), click());
        onView(withId(R.id.color_blue))
                .perform(scrollTo(), click());
    }

    /*
     * Check that frequency can be changed to any option
     */
    @Test
    public void selectFrequency() {
        String freq = "Daily";
        EspressoHelper.selectDropdownOption(R.id.spinner1, freq);
        onView(withId(R.id.spinner1))
                .check(matches(withSpinnerText(containsString(freq))));
        freq = "Weekly";
        EspressoHelper.selectDropdownOption(R.id.spinner1, freq);
        onView(withId(R.id.spinner1))
                .check(matches(withSpinnerText(containsString(freq))));
        freq = "Monthly";
        EspressoHelper.selectDropdownOption(R.id.spinner1, freq);
        onView(withId(R.id.spinner1))
                .check(matches(withSpinnerText(containsString(freq))));
        freq = "Yearly";
        EspressoHelper.selectDropdownOption(R.id.spinner1, freq);
        onView(withId(R.id.spinner1))
                .check(matches(withSpinnerText(containsString(freq))));
        freq = "Only once";
        EspressoHelper.selectDropdownOption(R.id.spinner1, freq);
        onView(withId(R.id.spinner1))
                .check(matches(withSpinnerText(containsString(freq))));
    }

    /*
     * Check that form can be submitted
     * - Note: Filled out only "Name" field as other ones are not required or have default values
     */
    @Test
    public void btnSubmit() {
        String name = "testTask_" + dateStr;
        EspressoHelper.insertTextIntoInput(R.id.taskName, name);
        onView(withId(R.id.btnSubmit))
                .perform(scrollTo(), click());
        onView(withId(R.id.activity_main))
                .check(matches(isDisplayed()));
    }


}