package com.example.sprintproject.activities.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.viewmodel.WelcomeViewModel;
import com.example.sprintproject.databinding.ActivityWelcomePageBinding;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWelcomePageBinding binding = ActivityWelcomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WelcomeViewModel welcomeViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
        binding.setViewModel(welcomeViewModel);
        binding.setLifecycleOwner(this);

        // Reference to the TextViews and Buttons
        Button startButton = findViewById(R.id.start_button);
        Button quitButton = findViewById(R.id.quit_button);
        Button signOutButton = findViewById(R.id.sign_out_button);

        // Set onClickListener for the Start button to open Login page
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, (welcomeViewModel.userIsLoggedIn()) ? MainActivity.class : LogInActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for the Quit button to exit the app
        quitButton.setOnClickListener(v -> {
            finish(); // Close the current activity
            System.exit(0); // Exit the app
        });

        signOutButton.setOnClickListener(v -> {
            welcomeViewModel.signOut();
        });
    }
}