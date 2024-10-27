package com.example.sprintproject.activities.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
        EditText travelLocationInput = findViewById(R.id.travelLocationInput);
        EditText estimatedStartInput = findViewById(R.id.estimatedStartDateInput);
        EditText estimatedEndInput = findViewById(R.id.estimatedEndDateInput);
        Button logTravelButton = findViewById(R.id.logTravelButton);

        // Set the onClickListener for the Log Travel button
        logTravelButton.setOnClickListener(v -> {
            String travelLocation = travelLocationInput.getText().toString().trim();
            String estimatedStart = estimatedStartInput.getText().toString().trim();
            String estimatedEnd = estimatedEndInput.getText().toString().trim();

            // Input validation
            if (travelLocation.isEmpty()) {
                Toast.makeText(LogTravelActivity.this, "Please enter a travel location",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (estimatedStart.isEmpty()) {
                Toast.makeText(LogTravelActivity.this, "Please enter an estimated start date",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (estimatedEnd.isEmpty()) {
                Toast.makeText(LogTravelActivity.this, "Please enter an estimated end date",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Trigger ViewModel method to handle data logging
            logTravelViewModel.logTravelData(travelLocation, estimatedStart, estimatedEnd);
            Toast.makeText(LogTravelActivity.this, "Travel data logged successfully",
                    Toast.LENGTH_SHORT).show();

            // Clear input fields after successful logging
            travelLocationInput.setText("");
            estimatedStartInput.setText("");
            estimatedEndInput.setText("");
        });
    }
}