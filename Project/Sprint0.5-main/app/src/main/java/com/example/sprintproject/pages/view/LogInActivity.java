package com.example.sprintproject.pages.view;

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
import com.example.sprintproject.pages.viewmodel.LogInViewModel;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        LogInViewModel logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        if (logInViewModel.userIsLoggedIn()) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Apply edge-to-edge insets for the activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                Toast.makeText(LogInActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LogInActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                logInViewModel.login(username, password);
            } catch (Authentication.AuthenticationFailure e) {
                // Login failure
                Toast.makeText(LogInActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            // Login success
            Toast.makeText(LogInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
        });
    }
}