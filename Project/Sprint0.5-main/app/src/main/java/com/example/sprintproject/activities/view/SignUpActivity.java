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
import com.example.sprintproject.databinding.ActivitySignUpPageBinding;
import com.example.sprintproject.model.Authentication;
import com.example.sprintproject.activities.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    // Sign up page goes here if we do not have an account currently

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignUpPageBinding binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding.setViewModel(signUpViewModel);
        binding.setLifecycleOwner(this);

        if (signUpViewModel.userIsLoggedIn()) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Reference to input fields and button
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button signUpButton = findViewById(R.id.signup_button);

        // Set the onClickListener for the Sign Up button
        signUpButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Input validation
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(SignUpActivity.this,
                        "Please enter an email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUpActivity.this,
                        "Please enter a password", Toast.LENGTH_SHORT).show();
                return;
            }

            signUpViewModel.signUp(email, password, new Authentication.AuthCallback() {
                @Override
                public void onSuccess() {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(SignUpActivity.this,
                            "Sign Up failed: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
