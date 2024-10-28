package com.example.sprintproject.fragments.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.activities.view.LogTravelActivity;
import com.example.sprintproject.activities.view.VacationTimeActivity;

public class DestinationFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public DestinationFragment() {
        // Required empty public constructor
    }

    public static DestinationFragment newInstance(String param1, String param2) {
        DestinationFragment fragment = new DestinationFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

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

        return view;
    }
}