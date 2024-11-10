package com.example.sprintproject.fragments.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.example.sprintproject.model.DiningReservation;
import java.util.ArrayList;
import java.util.List;

public class DiningViewModel extends ViewModel {

    private final MutableLiveData<List<DiningReservation>> reservations = new MutableLiveData<>(new ArrayList<>());
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration;

    public LiveData<List<DiningReservation>> getReservations() {
        return reservations;
    }

    public void addReservation(DiningReservation reservation) {
        db.collection("diningReservations").add(reservation);  // Add to Firestore
    }

    public void loadReservations() {
        listenerRegistration = db.collection("diningReservations")
                .addSnapshotListener((querySnapshot, e) -> {
                    if (e != null || querySnapshot == null) return;

                    List<DiningReservation> updatedReservations = new ArrayList<>();
                    querySnapshot.forEach(doc -> {
                        DiningReservation reservation = doc.toObject(DiningReservation.class);
                        updatedReservations.add(reservation);
                    });
                    reservations.setValue(updatedReservations);
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (listenerRegistration != null) listenerRegistration.remove();
    }
}
