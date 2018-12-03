package com.team27.killddl.data;

/**
 * Created by adithya on 10/19/18.
 */

public class Task {

    private String name;
    private String description;
    private int priority;
    private String date;
    private int complete;
    public Task(String name, String description, int priority, String date) {
        setName(name);
        setDescription(description);
        setPriority(priority);
        setDate(date);
        complete = 0;
    }

    public Task(String name, String description, int priority, String date, int comp) {
        setName(name);
        setDescription(description);
        setPriority(priority);
        setDate(date);
        complete = comp;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public void markComplete(){
        complete = 1;
    }

    public int isComplete(){
        return complete;
    }
}
