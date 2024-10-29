package com.example.sprintproject.fragments.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DateDifferenceCalculator;
import com.example.sprintproject.model.DestinationDatabase;

import java.util.List;

public class DestinationViewModel extends ViewModel {
    private MutableLiveData<Integer> totalVacationTime = new MutableLiveData<>(0);

    public DestinationViewModel(LifecycleOwner lifecycleOwner) {
        DestinationDatabase destinationDatabase = DestinationDatabase.getInstance();

        destinationDatabase.getTravelLogs().observe(lifecycleOwner, this::updateTotalVacationTime);
    }

    public MutableLiveData<Integer> getTotalVacationTime() {
        return totalVacationTime;
    }

    private void updateTotalVacationTime(List<DestinationDatabase.TravelLog> travelLogList) {
        int totalTime = 0;

        for (DestinationDatabase.TravelLog travelLog : travelLogList) {
            totalTime += DateDifferenceCalculator.calculateDifference(travelLog.startDate, travelLog.endDate);
        }

        totalVacationTime.postValue(totalTime);
    }
}
