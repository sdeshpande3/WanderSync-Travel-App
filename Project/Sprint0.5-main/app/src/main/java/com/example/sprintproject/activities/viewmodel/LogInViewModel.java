package com.example.sprintproject.activities.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Authentication;
import com.google.firebase.auth.FirebaseUser;
// we are brought here when we need to log in
public class LogInViewModel extends ViewModel {
    public LogInViewModel() {
    }

    public void login(String username, String password, Authentication.AuthCallback callback) {
        Authentication.getInstance().login(username, password, callback);
    }

    public boolean userIsLoggedIn() {
        MutableLiveData<FirebaseUser> userLiveData = Authentication.getInstance().getUserLiveData();

        return userLiveData != null && userLiveData.getValue() != null;
    }
}
