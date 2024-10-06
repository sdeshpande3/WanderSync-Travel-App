package com.example.sprintproject.activities.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        // Reference to the TextViews and Buttons
        Button startButton = findViewById(R.id.start_button);
        Button quitButton = findViewById(R.id.quit_button);

        // Set onClickListener for the Start button to open Login page
//        startButton.setOnClickListener(v -> {
//            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//            startActivity(intent);
//        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the Quit button to exit the app
//        quitButton.setOnClickListener(v -> {
//            finish(); // Close the current activity
//            System.exit(0); // Exit the app
//        });
    }
}