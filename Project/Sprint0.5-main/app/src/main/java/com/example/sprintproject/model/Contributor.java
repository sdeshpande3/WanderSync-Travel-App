package com.example.sprintproject.model;

public class Contributor {
    private String email;
    private boolean status;  // Indicates if the contributor is active

    // Default constructor required for Firebase
    public Contributor() {}

    public Contributor(String email, boolean status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
