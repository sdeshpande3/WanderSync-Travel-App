package com.example.sprintproject.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDatabase {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference collectionRef;

    private static UserDatabase instance;

    private final MutableLiveData<Map<String, String>> vacationSettings = new MutableLiveData<>();

    private UserDatabase() {
        MutableLiveData<FirebaseUser> user = Authentication.getInstance().getUserLiveData();

        if (user.getValue() != null) {
            collectionRef = firestore.collection("users").document(user.getValue().getUid());

            collectionRef.addSnapshotListener((snapshot, e) -> {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> settings = (Map<String, Object>) snapshot.get("vacationSettings");

                    if (settings != null) {
                        Map<String, String> newSettings = new HashMap<>();

                        settings.forEach((key, value) -> {
                           newSettings.put(key, (String) value);
                        });

                        vacationSettings.postValue(newSettings);
                    } else {
                        // Clear travel logs
                        vacationSettings.postValue(Map.of());
                    }
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("vacationSettings", new HashMap<>());
                    collectionRef.set(data).addOnSuccessListener(f -> {
                        Log.d("DestinationDatabase", "Created vacation settings");
                    }).addOnFailureListener(f -> {
                        Log.e("DestinationDatabase", "Error creating vacation settings");
                    });
                    vacationSettings.postValue(new HashMap<>());
                }
            });
        }
    }

    public static synchronized UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }

        return instance;
    }

    public MutableLiveData<Map<String, String>> getVacationSettings() {
        return vacationSettings;
    }

    public void setVacationSetting(Map<String, String> settings) {
        collectionRef.update("vacationSettings", settings).addOnSuccessListener(f -> {
            Log.d("DestinationDatabase", "Updated vacation settings");
        }).addOnFailureListener(f -> {
            Log.e("DestinationDatabase", "Error updating vacation settings");
        });
    }
}
