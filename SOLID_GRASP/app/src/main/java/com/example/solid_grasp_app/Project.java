package com.example.solid_grasp_app;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<Task> tasks = new ArrayList<>();
    private List<TeamMember> teamMembers = new ArrayList<>();

    // Constructor, getters, and setters

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void addTeamMember(TeamMember member) {
        teamMembers.add(member);
    }

    public void removeTeamMember(TeamMember member) {
        teamMembers.remove(member);
    }
}
