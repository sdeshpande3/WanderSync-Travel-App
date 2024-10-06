package com.example.sprintproject.activities.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Authentication;
import com.example.sprintproject.activities.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        SignUpViewModel signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        if (signUpViewModel.userIsLoggedIn()) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Apply edge-to-edge insets for the activity
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

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
                Toast.makeText(SignUpActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUpActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                signUpViewModel.signUp(email, password);
            } catch (Authentication.AuthenticationFailure e) {
                // Sign up failure
                Toast.makeText(SignUpActivity.this, "Sign Up failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            // Sign up success
            Toast.makeText(SignUpActivity.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();
        });
    }
}
