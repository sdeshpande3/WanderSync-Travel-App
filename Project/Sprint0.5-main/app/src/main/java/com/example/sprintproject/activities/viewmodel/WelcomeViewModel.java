package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;

public class WelcomeViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public WelcomeViewModel() {
        this.authenticationModel = new Authentication();
    }

    public boolean userIsLoggedIn() {
        return this.authenticationModel.getUserLiveData().getValue() != null;
    }
}
