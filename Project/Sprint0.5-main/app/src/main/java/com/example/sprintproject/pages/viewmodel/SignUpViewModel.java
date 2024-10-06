package com.example.sprintproject.pages.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;

public class SignUpViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public SignUpViewModel() {
        this.authenticationModel = new Authentication();
    }

    public void signUp(String username, String password) {
        authenticationModel.register(username, password);
    }

    public boolean userIsLoggedIn() {
        return this.authenticationModel.getUserLiveData().getValue() != null;
    }
}
