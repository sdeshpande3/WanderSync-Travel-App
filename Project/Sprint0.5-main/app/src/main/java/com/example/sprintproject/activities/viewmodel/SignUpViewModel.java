package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends ViewModel {
    private final Authentication authenticationModel;

    public SignUpViewModel() {
        this.authenticationModel = new Authentication();
    }

    public void signUp(String username, String password, Authentication.AuthCallback callback) {
        this.authenticationModel.register(username, password, callback);
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = this.authenticationModel.getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }
}
