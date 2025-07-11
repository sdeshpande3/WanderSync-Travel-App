package com.example.sprintproject.model;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationDatabase {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference collectionRef;

    private static DestinationDatabase instance;

    // Every piece of data that belongs to the user
    private final MutableLiveData<ArrayList<TravelLog>> travelLogs =
            new MutableLiveData<>(new ArrayList<>());

    private DestinationDatabase() {
        MutableLiveData<FirebaseUser> user = Authentication.getInstance().getUserLiveData();

        if (user.getValue() != null) {
            collectionRef = firestore.collection("destinations").document(user.getValue().getUid());

            collectionRef.addSnapshotListener((snapshot, e) -> {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    List<Map<String, Object>> logs =
                            (List<Map<String, Object>>) snapshot.get("travelLogs");

                    if (logs != null) {
                        ArrayList<TravelLog> newTravelLogs = new ArrayList<>();

                        for (Map<String, Object> log : logs) {
                            String location = (String) log.get("location");
                            String startDate = (String) log.get("startDate");
                            String endDate = (String) log.get("endDate");
                            TravelLog travelLog = new TravelLog(location, startDate, endDate);

                            newTravelLogs.add(travelLog);
                        }

                        travelLogs.postValue(newTravelLogs);
                    } else {
                        // Clear travel logs
                        travelLogs.setValue(new ArrayList<>());
                    }
                } else {
                    Map<String, Object> data = new HashMap<>();

                    ArrayList<TravelLog> sampleTravelLogs = new ArrayList<>();
                    TravelLog sample1 = new TravelLog(
                            "Sample 1",
                            "12/22/2024",
                            "12/24/2024"
                    );
                    TravelLog sample2 = new TravelLog(
                            "Sample 2",
                            "12/25/2024",
                            "12/27/2024"
                    );

                    sampleTravelLogs.add(sample1);
                    sampleTravelLogs.add(sample2);

                    data.put("travelLogs", sampleTravelLogs);

                    collectionRef.set(data).addOnSuccessListener(f -> {
                        Log.d("DestinationDatabase", "Created destination collection");
                    }).addOnFailureListener(f -> {
                        Log.e("DestinationDatabase", "Error creating destionation collection");
                    });
                    travelLogs.setValue(new ArrayList<>());
                }
            });
        }
    }

    public static synchronized DestinationDatabase getInstance() {
        if (instance == null) {
            instance = new DestinationDatabase();
        }

        return instance;
    }

    /**
     * Get travel logs non-mutable live data
     *
     * @return Travel logs live data
     */
    public MutableLiveData<ArrayList<TravelLog>> getTravelLogs() {
        return travelLogs;
    }

    public void addTravelLog(TravelLog travelLog) {
        List<Map<String, Object>> logs = new ArrayList<>();

        for (TravelLog log : travelLogs.getValue()) {
            Map<String, Object> logMap = new HashMap<>();

            logMap.put("location", log.location);
            logMap.put("startDate", log.startDate);
            logMap.put("endDate", log.endDate);

            logs.add(logMap);
        }

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("location", travelLog.getLocation());
        logMap.put("startDate", travelLog.getStartDate());
        logMap.put("endDate", travelLog.getEndDate());
        logs.add(logMap);

        collectionRef.update("travelLogs", logs).addOnSuccessListener(e -> {
            Log.d("DestinationDatabase", "Updated travel logs");
        }).addOnFailureListener(e -> {
            Log.e("DestinationDatabase", "Error updating travel logs");
        });
    }

    public static class TravelLog {
        private String location;
        private String startDate;
        private String endDate;

        public TravelLog() {

        }

        public TravelLog(String location, String startDate, String endDate) {
            this.location = location;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getLocation() {
            return location;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        @NonNull
        public String toString(){ return location + " " + startDate + " " + endDate; }
    }
}