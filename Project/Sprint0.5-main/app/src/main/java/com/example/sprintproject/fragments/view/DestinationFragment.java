package com.example.sprintproject.fragments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.view.LogTravelActivity;
import com.example.sprintproject.activities.view.VacationTimeActivity;
import com.example.sprintproject.fragments.viewmodel.DestinationViewModel;
import com.example.sprintproject.model.DateDifferenceCalculator;
import com.example.sprintproject.model.DestinationDatabase;

import java.util.ArrayList;

public class DestinationFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        DestinationViewModel viewModel = new DestinationViewModel(getViewLifecycleOwner());

        // Set up the Log Travel button to open LogTravelActivity
        Button logTravelButton = view.findViewById(R.id.logTravelButton);
        logTravelButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LogTravelActivity.class);
            startActivity(intent);
        });

        Button calculateVacationTime = view.findViewById(R.id.calculateVacationTimeButton);
        calculateVacationTime.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), VacationTimeActivity.class);
            startActivity(intent);
        });

        DestinationDatabase
                .getInstance()
                .getTravelLogs().observe(getViewLifecycleOwner(), this::displayTravelLogs);

        TextView totalVacationTimeText = view.findViewById(R.id.totalVacationTime);

        viewModel.getTotalVacationTime().observe(getViewLifecycleOwner(), totalVacationTime -> {
            totalVacationTimeText.setText("Total Vacation Time: " + totalVacationTime + " days");
        });

        return view;
    }

    private void displayTravelLogs(ArrayList<DestinationDatabase.TravelLog> logs) {
        if (getView() == null) {
            return;
        }

        LinearLayout travelLogContainer = getView().findViewById(R.id.layoutTravelLogs);
        travelLogContainer.removeAllViews();

        for (DestinationDatabase.TravelLog log : logs) {
            LinearLayout row = createRow(
                    log.getLocation(),
                    DateDifferenceCalculator.calculateDifference(log.getStartDate(), log.getEndDate()
                    )
            );

            travelLogContainer.addView(row);
            travelLogContainer.addView(createDivider());
        }
    }

    private LinearLayout createRow(String destination, int daysPlanned) {
        LinearLayout row = new LinearLayout(getContext());
        row.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 16, 0, 16); // Add padding for spacing

        // Create Destination TextView
        TextView destinationTextView = new TextView(getContext());
        destinationTextView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                )
        );
        destinationTextView.setText(destination);
        destinationTextView.setTextSize(16);
        destinationTextView.setGravity(Gravity.START);
        destinationTextView.setTextColor(
                ContextCompat.getColor(getContext(), android.R.color.black)
        );
        destinationTextView.setTypeface(null, android.graphics.Typeface.BOLD);

        // Create Days Planned TextView
        TextView daysPlannedTextView = new TextView(getContext());
        daysPlannedTextView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        daysPlannedTextView.setText(daysPlanned + " days planned");
        daysPlannedTextView.setTextSize(14);
        daysPlannedTextView.setGravity(Gravity.END);
        daysPlannedTextView.setTextColor(
                ContextCompat.getColor(getContext(), android.R.color.darker_gray)
        );

        // Add both TextViews to the row
        row.addView(destinationTextView);
        row.addView(daysPlannedTextView);

        return row;
    }

    private View createDivider() {
        View divider = new View(getContext());

        divider.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
        );

        divider.setBackgroundColor(
                ContextCompat.getColor(getContext(), android.R.color.darker_gray)
        );

        return divider;
    }


}