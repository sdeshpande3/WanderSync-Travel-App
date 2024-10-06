package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;

public class LogInViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public LogInViewModel() {
        this.authenticationModel = new Authentication();
    }

    public void login(String username, String password) {
        authenticationModel.login(username, password);
    }

    public boolean userIsLoggedIn() {
        return this.authenticationModel.getUserLiveData().getValue() != null;
    }
}
