package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class LogInViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public LogInViewModel() {
        this.authenticationModel = new Authentication();
    }

    public void login(String username, String password, Authentication.AuthCallback callback) {
        this.authenticationModel.login(username, password, callback);
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = this.authenticationModel.getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }
}
