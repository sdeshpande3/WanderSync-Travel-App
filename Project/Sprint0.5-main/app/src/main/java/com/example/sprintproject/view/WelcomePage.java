package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        // Reference to the TextViews and Buttons
        TextView welcomeText = findViewById(R.id.welcome_text);
        TextView appNameText = findViewById(R.id.app_name_text);
        Button startButton = findViewById(R.id.start_button);
        Button quitButton = findViewById(R.id.quit_button);

        // Set onClickListener for the Start button to open Login page
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, logInPage.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the Quit button to exit the app
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
                System.exit(0); // Exit the app
            }
        });
    }
}