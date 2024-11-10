package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;


//This is the view model for sign up.
public class SignUpViewModel extends ViewModel {
    public SignUpViewModel() {
    }

    public void signUp(String username, String password, Authentication.AuthCallback callback) {
        Authentication.getInstance().register(username, password, callback);
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = Authentication.getInstance().getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }
}
