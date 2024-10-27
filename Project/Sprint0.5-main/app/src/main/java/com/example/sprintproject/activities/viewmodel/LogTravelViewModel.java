package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LogTravelViewModel extends ViewModel {

    // LiveData properties for data binding
    public MutableLiveData<String> travelLocation = new MutableLiveData<>("");
    public MutableLiveData<String> estimatedStart = new MutableLiveData<>("");
    public MutableLiveData<String> estimatedEnd = new MutableLiveData<>("");

    // List to store travel logs temporarily
    private List<TravelLog> travelLogs = new ArrayList<>();

    // Method to log travel data
    public void logTravelData(String location, String start, String end) {
        // Validate inputs
        if (location == null || location.isEmpty() || start == null || start.isEmpty() || end == null || end.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        // Create a new TravelLog object and add it to the list
        TravelLog newLog = new TravelLog(location, start, end);
        travelLogs.add(newLog);

        // Print the travel log to the console (simulating saving)
        System.out.println("Travel logged: Location - " + location + ", Start - " + start + ", End - " + end);

        // Clear LiveData fields after logging
        travelLocation.setValue("");
        estimatedStart.setValue("");
        estimatedEnd.setValue("");
    }

    // Simple inner class to represent a travel log
    private static class TravelLog {
        String location;
        String start;
        String end;

        TravelLog(String location, String start, String end) {
            this.location = location;
            this.start = start;
            this.end = end;
        }
    }
}