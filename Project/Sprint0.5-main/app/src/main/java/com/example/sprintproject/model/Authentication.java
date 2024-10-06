package com.example.sprintproject.model;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    private final FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;

    public Authentication() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return this.userLiveData;
    }

    public static class AuthenticationFailure extends RuntimeException {
        public AuthenticationFailure(String message) {
            super(message);
        }
    }

    /**
     * Login a user with username and password
     * @param username Username to sign in with
     * @param password Password to sign in with
     */
    public void login(String username, String password) throws AuthenticationFailure {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(firebaseAuth.getCurrentUser());
            } else {
                userLiveData.postValue(null);

                if (task.getException() != null) {
                    throw new AuthenticationFailure("Unknown error");
                } else {
                    throw new AuthenticationFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void register(String username, String password) {
        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.postValue(firebaseAuth.getCurrentUser());
            } else {
                userLiveData.postValue(null);

                if (task.getException() != null) {
                    throw new AuthenticationFailure("Unknown error");
                } else {
                    throw new AuthenticationFailure(task.getException().getMessage());
                }
            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
        userLiveData.postValue(null);
    }
}
