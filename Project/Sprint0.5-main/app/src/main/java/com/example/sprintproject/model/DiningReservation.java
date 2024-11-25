package com.example.sprintproject.model;

import androidx.annotation.NonNull;

public class DiningReservation {
    private String location;
    private String website;
    private float review; // Ensure this is primitive for Firebase
    private String reservationTime;

    // No-argument constructor required by Firebase
    public DiningReservation() {
    }

    public DiningReservation(String location, String website, float review, String reservationTime) {
        this.location = location;
        this.website = website;
        this.review = review;
        this.reservationTime = reservationTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    @NonNull
    public String toString() {
        return location + " " + reservationTime;
    }

}
