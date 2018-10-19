package com.team27.killddl.data;

/**
 * Created by adithya on 10/19/18.
 */

public class Task {

    private String name;
    private String description;
    private int priority;
    private int date;

    public Task(String name, String description, int priority, int date) {
        setName(name);
        setDescription(description);
        setPriority(priority);
        setDate(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
