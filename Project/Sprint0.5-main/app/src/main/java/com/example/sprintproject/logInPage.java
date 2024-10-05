package com.example.sprintproject;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class logInPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Reference to input fields and button
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        Button signUpButton = findViewById(R.id.signup_button);

        // Apply edge-to-edge insets for the activity
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the onClickListener for the Sign Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Input validation
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(logInPage.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(logInPage.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call signUp method
                signUp(email, password);
            }
        });
    }

    public void signUp(String email, String password) {
        // Sign up with Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-up success
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Save the user's email to the Realtime Database under their user ID
                                String userId = user.getUid();
                                saveEmailToDatabase(userId, email);
                            }
                            Toast.makeText(logInPage.this, "Sign Up successful!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign-up failure
                            Toast.makeText(logInPage.this, "Sign Up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Method to save email to Firebase Realtime Database
    private void saveEmailToDatabase(String userId, String email) {
        // Create a user object to store email
        User user = new User(email);

        // Save the user email under "Users" node with their UID as the key
        databaseReference.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(logInPage.this, "Email saved to database.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(logInPage.this, "Failed to save email: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // User class to store email (could be expanded with more fields)
    public static class User {
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String email) {
            this.email = email;
        }
    }
}
