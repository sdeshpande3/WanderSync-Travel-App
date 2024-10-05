package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sprintproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class logInPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Reference to input fields and buttons
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        Button createAccountButton = findViewById(R.id.create_account_button);

        // Apply edge-to-edge insets for the activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the onClickListener for the Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Input validation
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(logInPage.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(logInPage.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call login method
                login(username, password);
            }
        });

        // Set the onClickListener for the Create Account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Sign Up page
                Intent intent = new Intent(logInPage.this, signUpPage.class);
                startActivity(intent);
            }
        });
    }

    public void login(String username, String password) {
        // Login with Firebase Auth
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login success
                            Toast.makeText(logInPage.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            // You can navigate to another activity here if needed
                        } else {
                            // Login failure
                            Toast.makeText(logInPage.this, "Login failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}