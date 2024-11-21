package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DestinationDatabase;

import java.util.regex.Pattern;

//This is the model for user log travel
public class LogTravelViewModel extends ViewModel {
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    // LiveData properties for data binding
    private MutableLiveData<String> travelLocation = new MutableLiveData<>("Default Location");
    private MutableLiveData<String> estimatedStart = new MutableLiveData<>("01/01/2024");
    private MutableLiveData<String> estimatedEnd = new MutableLiveData<>("12/31/2024");

    public MutableLiveData<String> getTravelLocation() {
        return travelLocation;
    }

    public MutableLiveData<String> getEstimatedStart() {
        return estimatedStart;
    }

    public MutableLiveData<String> getEstimatedEnd() {
        return estimatedEnd;
    }

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
        DestinationDatabase.TravelLog travelLog =
                new DestinationDatabase.TravelLog(travelLocation, estimatedStart, estimatedEnd);
        DestinationDatabase.getInstance().addTravelLog(travelLog);
    }

    private boolean isValidDate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}