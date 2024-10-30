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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    // Firebase references
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FirebaseAuth and FirebaseDatabase references
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

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
        // EditText usernameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        // EditText confirmPasswordInput = findViewById(R.id.confirm_password_input);
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
                    // On successful sign up, save the user information to Firebase Realtime Database
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        String userID = currentUser.getUid();

                        // Create a HashMap to store the user's details
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("email", email);

                        // Save the user's details under their UID
                        usersRef.child(userID).setValue(userMap).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Successfully added the user to the database, navigate to MainActivity
                                Toast.makeText(SignUpActivity.this,
                                        "Account created successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Failed to add user details to the database
                                Toast.makeText(SignUpActivity.this,
                                        "Failed to save user data: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Failed to get user information after sign up", Toast.LENGTH_SHORT).show();
                    }
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
