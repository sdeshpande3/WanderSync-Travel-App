package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.UserDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class VacationTimeViewModel extends ViewModel {
    private MutableLiveData<String> startDate = new MutableLiveData<>("");
    private MutableLiveData<String> endDate = new MutableLiveData<>("");
    private MutableLiveData<String> duration = new MutableLiveData<>("");

    public VacationTimeViewModel() {
        UserDatabase instance = UserDatabase.getInstance();

        instance.getVacationSettings().observeForever((settings) -> {
            startDate.setValue(settings.get("start"));
            endDate.setValue(settings.get("end"));
            duration.setValue(settings.get("duration"));
        });
    }

    public MutableLiveData<String> getStartDate() {
        return startDate;
    }

    public MutableLiveData<String> getEndDate() {
        return endDate;
    }

    public MutableLiveData<String> getDuration() {
        return duration;
    }

    public void calculateFieldsIfNeeded() {
        if (startDate.getValue() != null && endDate.getValue() != null
                && (duration.getValue() == null || duration.getValue().isEmpty())) {
            calculateDuration();
        } else if (startDate.getValue() != null && duration.getValue() != null
                && (endDate.getValue() == null || endDate.getValue().isEmpty())) {
            calculateEndDate();
        }
    }

    private void calculateDuration() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate start = LocalDate.parse(startDate.getValue(), formatter);
            LocalDate end = LocalDate.parse(endDate.getValue(), formatter);
            int calculatedDuration = (int) ChronoUnit.DAYS.between(start, end);
            duration.setValue(String.valueOf(calculatedDuration));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    private void calculateEndDate() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate start = LocalDate.parse(startDate.getValue(), formatter);
            int days = Integer.parseInt(duration.getValue());
            LocalDate calculatedEndDate = start.plusDays(days);
            endDate.setValue(calculatedEndDate.format(formatter));
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date or duration");
        }
    }

    public void saveVacationData() {
        String start = startDate.getValue();
        String end = endDate.getValue();
        String dur = duration.getValue();

        if (
                start == null
                        || start.isEmpty()
                        || end == null
                        || end.isEmpty()
                        || dur == null
                        || dur.isEmpty()
        ) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate.parse(start, formatter);
            LocalDate.parse(end, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }

        Map<String, String> settings = new HashMap<>();
        settings.put("start", start);
        settings.put("end", end);

        UserDatabase.getInstance().setVacationSetting(settings);
    }
}
