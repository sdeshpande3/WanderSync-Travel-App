package com.example.solid_grasp_app;

public abstract class Task {
    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Priority priority;

    // Constructor, getters, and setters

    public abstract void completeTask();
}


