package com.team27.killddl;

import com.team27.killddl.data.Task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TaskUnitTest {

    private static final Task SIMPLE_TASK =
            new Task("name", "description", 0, "date");

    private class TestRunner {

        private Task task;

        private TestRunner() {
            task = new Task("name", "description", 0, "date");
        }

        private TestRunner verifyGetName() {
            assertEquals(task.getName(), "name");
            return this;
        }

        private TestRunner verifyGetDescription() {
            assertEquals(task.getDescription(), "description");
            return this;
        }

        private TestRunner verifyGetPriority() {
            assertEquals(task.getPriority(), 0);
            return this;
        }

        private TestRunner verifyGetDate() {
            assertEquals(task.getDate(), "date");
            return this;
        }

        private TestRunner verifySetName() {
            task.setName("new_name");
            assertEquals(task.getName(), "new_name");
            return this;
        }

        private TestRunner verifySetDescription() {
            task.setDescription("new_description");
            assertEquals(task.getDescription(), "new_description");
            return this;
        }

        private TestRunner verifySetPriority() {
            task.setPriority(4);
            assertEquals(task.getPriority(), 4);
            return this;
        }

        private TestRunner verifySetDate() {
            task.setDate("new_date");
            assertEquals(task.getDate(), "new_date");
            return this;
        }
    }

    @Test
    public void TestTask_GetName() {
        new TestRunner().verifyGetName();
    }

    @Test
    public void TestTask_GetDescription() {
        new TestRunner().verifyGetDescription();
    }

    @Test
    public void TestTask_GetPriority() {
        new TestRunner().verifyGetPriority();
    }

    @Test
    public void TestTask_GetDate() {
        new TestRunner().verifyGetDate();
    }

    @Test
    public void TestTask_SetName() {
        new TestRunner().verifySetName();
    }

    @Test
    public void TestTask_SetDescription() {
        new TestRunner().verifySetDescription();
    }

    @Test
    public void TestTask_SetPriority() {
        new TestRunner().verifySetPriority();
    }

    @Test
    public void TestTask_SetDate() {
        new TestRunner().verifySetDate();
    }
}
