package com.example.sprintproject.model;

import androidx.lifecycle.LiveData;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FirestoreLiveData extends LiveData<DocumentSnapshot> {

    private final DocumentReference documentReference;
    private final MyEventListener listener = new MyEventListener();

    public FirestoreLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected void onActive() {
        documentReference.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        listener.remove();
    }

    private class MyEventListener implements com.google.firebase.firestore.EventListener<DocumentSnapshot> {
        @Override
        public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException error) {
            if (error != null) {
                return;
            }
            setValue(snapshot);
        }

        public void remove() {
            documentReference.addSnapshotListener(null);
        }
    }
}
