package com.example.sprintproject.activities.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.viewmodel.MainViewModel;
import com.example.sprintproject.databinding.ActivityMainBinding;
import com.example.sprintproject.fragments.view.DestinationFragment;
import com.example.sprintproject.fragments.view.LogisticsFragment;
import com.example.sprintproject.fragments.view.AccommodationsFragment;
import com.example.sprintproject.fragments.view.DiningFragment;
import com.example.sprintproject.fragments.view.TravelFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * Taken inspiration from this video: https://www.youtube.com/watch?v=jOFLmKMOcK0
     */


    private ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(mainViewModel);
        binding.setLifecycleOwner(this);

        // Home page
        replaceFragment(new LogisticsFragment());

        /**
         * Bottom navigation bar
         */
        binding.bottomNavigationView.setOnItemSelectedListener(clicked -> {
            /* Need to fix to use gaurd clauses*/
            if (clicked.getItemId() == R.id.logistics) {
                replaceFragment(new LogisticsFragment());
            } else if (clicked.getItemId() == R.id.destination) {
                replaceFragment(new DestinationFragment());
            } else if (clicked.getItemId() == R.id.dining) {
                replaceFragment(new DiningFragment());
            } else if (clicked.getItemId() == R.id.accommodations) {
                replaceFragment(new AccommodationsFragment());
            } else if (clicked.getItemId() == R.id.travel) {
                replaceFragment(new TravelFragment());
            }

            return true;
        });
    }

    /**
     * Replace fragment uses the fragment manager to do so
     @param fragment The fragment to replace the current fragment with
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}


