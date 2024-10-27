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

public class DestinationFragment extends Fragment {

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

        return view;
    }
}