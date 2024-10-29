package com.example.sprintproject.fragments.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DestinationDatabase;

import java.util.ArrayList;

public class DestinationViewModel extends ViewModel {
    private LiveData<ArrayList<DestinationDatabase.TravelLog>> travelLogs;

    public DestinationViewModel() {
        this.travelLogs = DestinationDatabase.getInstance().getTravelLogs();
    }
}
