package com.example.sprintproject.activities.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityLogInPageBinding;
import com.example.sprintproject.model.Authentication;
import com.example.sprintproject.activities.viewmodel.LogInViewModel;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLogInPageBinding binding = ActivityLogInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LogInViewModel logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);
        binding.setViewModel(logInViewModel);
        binding.setLifecycleOwner(this);

        if (logInViewModel.userIsLoggedIn()) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Reference to input fields and buttons
        EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        Button createAccountButton = findViewById(R.id.create_account_button);

        // Navigate to the Sign Up page
        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Set the onClickListener for the Login button
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Input validation
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(LogInActivity.this, "Please enter a username",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LogInActivity.this, "Please enter a password",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            logInViewModel.login(username, password, new Authentication.AuthCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(LogInActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(LogInActivity.this,
                            "Login failed: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}