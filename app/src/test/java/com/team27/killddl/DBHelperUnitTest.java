package com.team27.killddl;


import android.os.Build;

import com.team27.killddl.data.DBHelper;
import com.team27.killddl.data.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        manifest = "app/manifests/AndroidManifest.xml",
        constants = BuildConfig.class)
public class DBHelperUnitTest {

    private static final String TASK_NAME = "name";
    private static final String SECOND_TASK_NAME = "new_name";
    private static final String TASK_DESCRIPTION = "description";
    private static final int TASK_PRIORITY = 0;
    private static final String TASK_DATE = "date";

    private static final String DATE = "2018-01-01";
    private static final String INVALID_FIXED_DATE = "2018-03-12";

    private static final Task SIMPLE_TASK =
            new Task(TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE);

    private class TestRunner {

        private DBHelper helper;

        private TestRunner() {
            helper = new DBHelper(RuntimeEnvironment.application);
        }

        private TestRunner insertTestTask(String name, String description, int priority, String date) {
            helper.insertNewTask(name, description, priority, date);
            return this;
        }

        private TestRunner verifySingleTask() {
            Task result = helper.getTask(TASK_NAME);
            assertTrue(
                    result.getName().equals(SIMPLE_TASK.getName()) &&
                            result.getDescription().equals(SIMPLE_TASK.getDescription()) &&
                            result.getPriority() == SIMPLE_TASK.getPriority() &&
                            result.getDate().equals(SIMPLE_TASK.getDate())
            );
            return this;
        }

        private TestRunner verifyNoTask() {
            assertNull(helper.getTask(TASK_NAME));
            return this;
        }

        private TestRunner verifyDateStringValidInput() {
            assertEquals(DBHelper.getDateString(2018, 0,1), DATE);
            return this;
        }

        private TestRunner verifyDateStringInvalidInput() {
            assertEquals(DBHelper.getDateString(2018, 0,71), INVALID_FIXED_DATE);
            return this;
        }

    }

    @Test
    public void TestDBHelper_InitializeDB() {
        new TestRunner();
    }

    @Test
    public void TestDBHelper_EmptyDB() {
        new TestRunner().verifyNoTask();
    }

    @Test
    public void TestDBHelper_SingleTaskInDB() {
        new TestRunner()
                .insertTestTask(TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .verifySingleTask();
    }

    @Test
    public void TestDBHelper_WrongTaskInDB() {
        new TestRunner()
                .insertTestTask(SECOND_TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .verifyNoTask();
    }

    @Test
    public void TestDBHelper_MultipleTasksInDB() {
        new TestRunner()
                .insertTestTask(TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .insertTestTask(SECOND_TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .verifySingleTask();
    }

    @Test
    public void TestDBHelper_DuplicateTaskInDB() {
        new TestRunner()
                .insertTestTask(TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .insertTestTask(TASK_NAME, TASK_DESCRIPTION, TASK_PRIORITY, TASK_DATE)
                .verifySingleTask();
    }

    @Test
    public void TestDBHelper_GetDateStringValidInput() {
        new TestRunner().verifyDateStringValidInput();
    }

    @Test
    public void TestDBHelper_GetDateStringInvalidInput() {
        new TestRunner().verifyDateStringInvalidInput();
    }
}
