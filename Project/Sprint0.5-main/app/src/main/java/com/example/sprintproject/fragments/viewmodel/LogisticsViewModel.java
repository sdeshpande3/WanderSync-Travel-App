package com.example.sprintproject.fragments.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Contributor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LogisticsViewModel extends ViewModel {

    private static final String TAG = "LogisticsViewModel";
    private final FirebaseFirestore firestore;

    public LogisticsViewModel() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void addContributor(String tripID, Contributor contributor, OnCompleteListener<Void> listener) {
        // Use Firestore's structure for trips and contributors
        firestore.collection("trips").document(tripID)
                .collection("contributors")
                .document(contributor.getEmail().replace(".", "_"))  // Replace '.' to avoid issues in Firestore
                .set(contributor)
                .addOnCompleteListener(listener)
                .addOnFailureListener(e -> Log.e(TAG, "Failed to add contributor: " + e.getMessage()));
    }

    public void getContributors(String tripID, ContributorsCallback callback) {
        firestore.collection("trips").document(tripID).collection("contributors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        ArrayList<Contributor> contributors = new ArrayList<>();
                        task.getResult().forEach(doc -> contributors.add(doc.toObject(Contributor.class)));
                        callback.onSuccess(contributors);
                    } else {
                        callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Unknown error occurred");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching contributors: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });
    }

    public void checkContributorExists(String tripID, String email, ContributorExistsCallback callback) {
        firestore.collection("trips").document(tripID)
                .collection("contributors").document(email.replace(".", "_"))
                .get()
                .addOnCompleteListener(task -> callback.onCheck(task.isSuccessful() && task.getResult() != null && task.getResult().exists()))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error checking contributor existence: " + e.getMessage());
                    callback.onCheck(false);
                });
    }

    public interface ContributorsCallback {
        void onSuccess(ArrayList<Contributor> contributors);
        void onFailure(String error);
    }

    public interface ContributorExistsCallback {
        void onCheck(boolean exists);
    }
}
