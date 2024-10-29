package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DestinationDatabase;

import java.util.regex.Pattern;

public class LogTravelViewModel extends ViewModel {
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    // LiveData properties for data binding
    public MutableLiveData<String> travelLocation = new MutableLiveData<>("");
    public MutableLiveData<String> estimatedStart = new MutableLiveData<>("");
    public MutableLiveData<String> estimatedEnd = new MutableLiveData<>("");

    // Method to log travel data
    public void logTravelData() {
        String travelLocation = this.travelLocation.getValue().trim();
        String estimatedStart = this.estimatedStart.getValue().trim();
        String estimatedEnd = this.estimatedEnd.getValue().trim();

        // Input validation
        if (travelLocation.isEmpty()) {
            throw new IllegalArgumentException("Please enter a travel location");
        }

        if (estimatedStart.isEmpty()) {
            throw new IllegalArgumentException("Please enter an estimated start date");
        }

        if (estimatedEnd.isEmpty()) {
            throw new IllegalArgumentException("Please enter an estimated end date");
        }

        if (!isValidDate(estimatedStart)) {
            throw new IllegalArgumentException("Invalid start date. Use MM/DD/YYYY format.");
        }

        if (!isValidDate(estimatedEnd)) {
            throw new IllegalArgumentException("Invalid end date. Use MM/DD/YYYY format.");
        }

        // Update DB
        DestinationDatabase.TravelLog travelLog = new DestinationDatabase.TravelLog(travelLocation, estimatedStart, estimatedEnd);
        DestinationDatabase.getInstance().addTravelLog(travelLog);
    }

    private boolean isValidDate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}