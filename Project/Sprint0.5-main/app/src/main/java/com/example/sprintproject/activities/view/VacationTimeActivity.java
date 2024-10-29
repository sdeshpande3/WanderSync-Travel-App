package com.example.sprintproject.activities.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.viewmodel.VacationTimeViewModel;
import com.example.sprintproject.databinding.ActivityVacationTimeBinding;

public class VacationTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVacationTimeBinding binding = ActivityVacationTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel and set up data binding
        VacationTimeViewModel vacationTimeViewModel = new ViewModelProvider(this).get(VacationTimeViewModel.class);
        binding.setViewModel(vacationTimeViewModel);
        binding.setLifecycleOwner(this);

        Button calculateButton = findViewById(R.id.calculateButton);
        Button saveButton = findViewById(R.id.saveButton);

        calculateButton.setOnClickListener(v -> {
            try {
                vacationTimeViewModel.calculateFieldsIfNeeded();
            } catch (Exception e) {
                Toast.makeText(VacationTimeActivity.this, "Error calculating fields: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set the onClickListener for the Save button
        saveButton.setOnClickListener(v -> {
            try {
                vacationTimeViewModel.calculateFieldsIfNeeded();
                vacationTimeViewModel.saveVacationData();
                Toast.makeText(VacationTimeActivity.this, "Vacation data saved successfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(VacationTimeActivity.this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}