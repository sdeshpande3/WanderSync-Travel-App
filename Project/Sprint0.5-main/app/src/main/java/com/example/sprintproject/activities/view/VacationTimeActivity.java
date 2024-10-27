package com.example.sprintproject.activities.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.viewmodel.VacationTimeViewModel;
import com.example.sprintproject.databinding.ActivityVacationTimeBinding;

public class VacationTimeActivity extends AppCompatActivity {

    private VacationTimeViewModel vacationTimeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityVacationTimeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vacation_time);

        // Initialize ViewModel and set up data binding
        vacationTimeViewModel = new ViewModelProvider(this).get(VacationTimeViewModel.class);
        binding.setViewModel(vacationTimeViewModel);
        binding.setLifecycleOwner(this);

        // Set the onClickListener for the Save button
        binding.saveButton.setOnClickListener(v -> {
            try {
                vacationTimeViewModel.saveVacationData();
                Toast.makeText(VacationTimeActivity.this, "Vacation data saved successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(VacationTimeActivity.this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}