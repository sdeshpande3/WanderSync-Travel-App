package com.example.sprintproject.model;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// handles all auth stuff, in single class
public class Authentication {

    private final FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;

    public Authentication() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            this.userLiveData.postValue(firebaseAuth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return this.userLiveData;
    }

    public interface AuthCallback {
        void onSuccess();

        void onFailure(String message);
    }

    /**
     * Login a user with username and password
     *
     * @param username Username to sign in with
     * @param password Password to sign in with
     */
    public void login(String username, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(firebaseAuth.getCurrentUser());
                callback.onSuccess();
            } else {
                userLiveData.postValue(null);

                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                callback.onFailure(errorMessage);
            }
        });
    }

    public void register(String username, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(firebaseAuth.getCurrentUser());

                callback.onSuccess();
            } else {
                userLiveData.postValue(null);

                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                callback.onFailure(errorMessage);
            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
        userLiveData.postValue(null);
    }
}
