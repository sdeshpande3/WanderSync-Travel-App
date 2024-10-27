package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.UserDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class VacationTimeViewModel extends ViewModel {
    private final MutableLiveData<String> startDate = new MutableLiveData<>("");
    private final MutableLiveData<String> endDate = new MutableLiveData<>("");
    private final MutableLiveData<String> duration = new MutableLiveData<>("");

    public LiveData<String> getStartDate() {
        return startDate;
    }

    public LiveData<String> getEndDate() {
        return endDate;
    }

    public LiveData<String> getDuration() {
        return duration;
    }

    public void setStartDate(String startDateInput) {
        startDate.setValue(startDateInput);
    }

    public void setEndDate(String endDateInput) {
        endDate.setValue(endDateInput);
    }

    public void setDuration(String durationInput) {
        duration.setValue(durationInput);
    }

    public void calculateFieldsIfNeeded() {
        if (startDate.getValue() != null && endDate.getValue() != null && (duration.getValue() == null || duration.getValue().isEmpty())) {
            calculateDuration();
        } else if (startDate.getValue() != null && duration.getValue() != null && (endDate.getValue() == null || endDate.getValue().isEmpty())) {
            calculateEndDate();
        }
    }

    private void calculateDuration() {
        try {
            LocalDate start = LocalDate.parse(startDate.getValue());
            LocalDate end = LocalDate.parse(endDate.getValue());
            int calculatedDuration = (int) ChronoUnit.DAYS.between(start, end);
            duration.setValue(String.valueOf(calculatedDuration));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    private void calculateEndDate() {
        try {
            LocalDate start = LocalDate.parse(startDate.getValue());
            int days = Integer.parseInt(duration.getValue());
            LocalDate calculatedEndDate = start.plusDays(days);
            endDate.setValue(calculatedEndDate.toString());
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date or duration");
        }
    }

    public void saveVacationData() {
        String start = startDate.getValue();
        String end = endDate.getValue();
        String dur = duration.getValue();

        if (start == null || start.isEmpty() || end == null || end.isEmpty() || dur == null || dur.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        UserDatabase.getInstance().saveVacationData(start, end, dur);
    }
}
