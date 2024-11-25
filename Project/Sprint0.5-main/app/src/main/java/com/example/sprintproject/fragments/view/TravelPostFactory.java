package com.example.sprintproject.fragments.view;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.model.DestinationDatabase.TravelLog;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;

import java.util.List;



public class TravelPostFactory {
    public static TravelPost createPost(String userId, String userEmail, String tripDuration,
                                        List<TravelLog> destinations,
                                        List<Accommodation> accommodations,
                                        List<DiningReservation> dining,
                                        String transportation, String notes) {
        return new TravelPost(userId, userEmail, tripDuration, destinations, accommodations, dining, transportation, notes, System.currentTimeMillis());
    }
}