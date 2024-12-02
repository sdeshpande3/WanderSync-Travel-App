package com.example.sprintproject.fragments.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.example.sprintproject.model.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class AccommodationViewModel extends ViewModel {

    private final MutableLiveData<List<Accommodation>> accommodations =
            new MutableLiveData<>(new ArrayList<>());
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration;

    public LiveData<List<Accommodation>> getAccommodations() {
        return accommodations;
    }

    public void addAccommodation(Accommodation accommodation) {
        db.collection("accommodations").add(accommodation); // Add to Firestore
    }

    public void loadAccommodations() {
        listenerRegistration = db.collection("accommodations")
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null || querySnapshot == null) {
                        return;
                    }

                    List<Accommodation> updatedAccommodations = new ArrayList<>();
                    querySnapshot.forEach(doc -> {
                        Accommodation accommodation = doc.toObject(Accommodation.class);
                        updatedAccommodations.add(accommodation);
                    });
                    accommodations.setValue(updatedAccommodations);
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (listenerRegistration