package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static UserDatabase instance;
    private final List<VacationData> vacationDataList;

    private UserDatabase() {
        vacationDataList = new ArrayList<>();
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public void saveVacationData(String startDate, String endDate, String duration) {
        vacationDataList.add(new VacationData(startDate, endDate, duration));
        System.out.println("Vacation saved: Start - " + startDate + ", End - " + endDate + ", Duration - " + duration);
    }

    public List<VacationData> getAllVacationData() {
        return new ArrayList<>(vacationDataList);
    }

    public static class VacationData {
        private final String startDate;
        private final String endDate;
        private final String duration;

        public VacationData(String startDate, String endDate, String duration) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.duration = duration;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getDuration() {
            return duration;
        }
    }
}
