package com.example.sprintproject.activities.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityLogTravelBinding;
import com.example.sprintproject.activities.viewmodel.LogTravelViewModel;

public class LogTravelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLogTravelBinding binding = ActivityLogTravelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel and set up data binding
        LogTravelViewModel logTravelViewModel = new ViewModelProvider(this).get(LogTravelViewModel.class);
        binding.setViewModel(logTravelViewModel);
        binding.setLifecycleOwner(this);

        // Reference to input fields and button
        Button cancelButton = findViewById(R.id.cancelButton);
        Button logTravelButton = findViewById(R.id.submitButton);

        cancelButton.setOnClickListener(v -> {
            finish();
        });

        // Set the onClickListener for the Log Travel button
        logTravelButton.setOnClickListener(v -> {
            // Trigger ViewModel method to handle data logging
            try {
                logTravelViewModel.logTravelData();
            } catch (IllegalArgumentException e) {
                Toast.makeText(LogTravelActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(LogTravelActivity.this, "Travel data logged successfully",
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }


}