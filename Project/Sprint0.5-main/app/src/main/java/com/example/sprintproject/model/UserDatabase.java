package com.example.sprintproject.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.example.sprintproject.model.Contributor;




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
                    Map<String, Object> settings =
                            (Map<String, Object>) snapshot.get("vacationSettings");

                    if (settings != null) {
                        Map<String, String> newSettings = new HashMap<>();

                        settings.forEach((key, value) -> {
                            newSettings.put(key, (String) value);
                        });

                        vacationSettings.postValue(newSettings);
                    } else {
                        vacationSettings.postValue(Map.of());
                    }
                } else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("vacationSettings", new HashMap<>());
                    collectionRef.set(data).addOnSuccessListener(f -> {
                        Log.d("UserDatabase", "Created vacation settings");
                    }).addOnFailureListener(f -> {
                        Log.e("UserDatabase", "Error creating vacation settings");
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
            Log.d("UserDatabase", "Updated vacation settings");
        }).addOnFailureListener(f -> {
            Log.e("UserDatabase", "Error updating vacation settings");
        });
    }

    // New Method for Adding Contributors
    public void addContributor(String tripID, Contributor contributor, OnCompleteListener<Void> listener) {
        DocumentReference tripRef = firestore.collection("trips").document(tripID);
        tripRef.collection("contributors")
                .document(contributor.getEmail()) // Use email as document ID
                .set(contributor)
                .addOnCompleteListener(listener)
                .addOnFailureListener(e -> Log.e("UserDatabase", "Failed to add contributor: " + e.getMessage()));
    }

    // New Method for Fetching Contributors (Optional for read purposes)
    public void getContributors(String tripID, ContributorsCallback callback) {
        firestore.collection("trips").document(tripID).collection("contributors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        ArrayList<Contributor> contributors = new ArrayList<>();
                        task.getResult().forEach(doc -> {
                            Contributor contributor = doc.toObject(Contributor.class);
                            contributors.add(contributor);
                        });
                        callback.onSuccess(contributors);
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public interface ContributorsCallback {
        void onSuccess(ArrayList<Contributor> contributors);
        void onFailure(String error);
    }
}
