package com.example.sprintproject.pages.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityMainBinding;
import com.example.sprintproject.fragments.view.CarFragment;
import com.example.sprintproject.fragments.view.CommunityFragment;
import com.example.sprintproject.fragments.view.DestinationFragment;
import com.example.sprintproject.fragments.view.HomeFragment;
import com.example.sprintproject.fragments.view.HotelFragment;
import com.example.sprintproject.fragments.view.RestaurantFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * Taken inspiration from this video: https://www.youtube.com/watch?v=jOFLmKMOcK0
     */
    ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();

        /**
         * Bottom navigation bar
         */
        ImageView homeImage = findViewById(R.id.home_image);
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HomeFragment());
            }
        });
        binding.bottomNavigationView.setOnItemSelectedListener(clicked -> {
            if (clicked.getItemId() == R.id.home_image) {
                replaceFragment(new HomeFragment());
            }
            if (clicked.getItemId() == com.example.sprintproject.R.id.destination) {
                replaceFragment(new DestinationFragment());
            }

            if (clicked.getItemId() == com.example.sprintproject.R.id.restaurant) {
                replaceFragment(new RestaurantFragment());
            }

            if (clicked.getItemId() == com.example.sprintproject.R.id.hotel) {
                replaceFragment(new HotelFragment());
            }

            if (clicked.getItemId() == R.id.community) {
                replaceFragment(new CommunityFragment());
            }
            if (clicked.getItemId() == R.id.car) {
                replaceFragment(new CarFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}


