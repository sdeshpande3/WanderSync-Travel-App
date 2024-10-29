package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeViewModel extends ViewModel {
    public WelcomeViewModel() {
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = Authentication.getInstance().getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }

    public void signOut() {
        Authentication.getInstance().logout();
    }
}
