package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public WelcomeViewModel() {
        this.authenticationModel = new Authentication();
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = this.authenticationModel.getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }

    public void signOut() {
        this.authenticationModel.logout();
    }
}
