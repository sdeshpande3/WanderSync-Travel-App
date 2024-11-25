package com.example.sprintproject.model;

import java.util.List;
import com.example.sprintproject.fragments.view.TravelPostFormatter;

public class TravelPost implements TravelPostFormatter {
    private String userId;
    private String userName;
    private String tripDuration;
    private List<DestinationDatabase.TravelLog> destinations;
    private List<Accommodation> accommodations;
    private List<DiningReservation> diningReservations;
    private String transportation;
    private String notes;
    private long timestamp;

    public TravelPost() {
        // Required empty constructor for Firebase
    }

    public TravelPost(String userId, String userName, String tripDuration,
                      List<DestinationDatabase.TravelLog> destinations,
                      List<Accommodation> accommodations,
                      List<DiningReservation> diningReservations,
                      String transportation, String notes, long timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.tripDuration = tripDuration;
        this.destinations = destinations;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
        this.transportation = transportation;
        this.notes = notes;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public List<DestinationDatabase.TravelLog> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationDatabase.TravelLog> destinations) {
        this.destinations = destinations;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public List<DiningReservation> getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(List<DiningReservation> diningReservations) {
        this.diningReservations = diningReservations;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String format() {
        return "Trip by: " + userId + "\n" +
                "Duration: " + tripDuration + "\n" +
                "Destinations: " + destinations.size() + " places\n";
    }
}
