package com.example.sprintproject.fragments.view;
import java.util.Collections;
import java.util.Comparator;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.RatingBar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.DiningViewModel;
import com.example.sprintproject.model.DiningReservation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.example.sprintproject.model.SortByTime;
import com.example.sprintproject.model.SortByType;
import com.example.sprintproject.model.SortStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiningFragment extends Fragment {

    private DiningViewModel diningViewModel;
    private RecyclerView recyclerView;
    private DiningAdapter adapter;

    public DiningFragment() {
        // Required empty public constructor
    }

    public static DiningFragment newInstance() {
        return new DiningFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dining, container, false);

        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DiningAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        diningViewModel.getReservations().observe(getViewLifecycleOwner(), reservations -> {
            adapter.updateData(reservations);
        });

        // Load data from Firestore
        diningViewModel.loadReservations();

        // Sorting buttons
        Button sortByTimeButton = view.findViewById(R.id.buttonSortByTime);
        Button sortByTypeButton = view.findViewById(R.id.buttonSortByReview); // Using "Type" for demonstration

        sortByTimeButton.setOnClickListener(v -> applySortStrategy(new SortByTime()));
        sortByTypeButton.setOnClickListener(v -> applySortStrategy(new SortByType()));

        View fab = view.findViewById(R.id.fabAddReservation);
        fab.setOnClickListener(v -> showAddReservationDialog());

        return view;
    }


    private void showTimePickerDialog(EditText timeField) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    timeField.setText(formattedTime);
                }, hour, minute, true);

        timePickerDialog.show();
    }
    private void showAddReservationDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_dining_reservation, null);
        dialog.setContentView(dialogView);

        EditText locationField = dialogView.findViewById(R.id.editTextLocation);
        EditText websiteField = dialogView.findViewById(R.id.editTextWebsite);
        EditText reservationTimeField = dialogView.findViewById(R.id.editTextReservationTime);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBarReview);
        Button saveButton = dialogView.findViewById(R.id.buttonSaveReservation);

        reservationTimeField.setOnClickListener(v -> showTimePickerDialog(reservationTimeField));

        saveButton.setOnClickListener(v -> {
            String location = locationField.getText().toString().trim();
            String website = websiteField.getText().toString().trim();
            String reservationTime = reservationTimeField.getText().toString().trim();
            float reviewRating = ratingBar.getRating();

            if (location.isEmpty() || website.isEmpty() || reservationTime.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isDuplicateReservation(location, reservationTime)) {
                Toast.makeText(getContext(), "A reservation with this location and time already exists.", Toast.LENGTH_SHORT).show();
                return;
            }

            DiningReservation reservation = new DiningReservation(location, website, reviewRating, reservationTime);
            diningViewModel.addReservation(reservation);

            Toast.makeText(getContext(), "Reservation added successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }


    private void applySortStrategy(SortStrategy strategy) {
        List<DiningReservation> sortedList = strategy.sort(new ArrayList<>(diningViewModel.getReservations().getValue()));
        adapter.updateData(sortedList);
    }


    public boolean validateReservationInput(String location, String website, String time, float rating) {
        return location != null && !location.isEmpty() &&
                website != null && !website.isEmpty() &&
                time != null && !time.isEmpty() &&
                rating > 0;
    }

    private boolean isDuplicateReservation(String location, String time) {
        List<DiningReservation> reservations = diningViewModel.getReservations().getValue();
        if (reservations != null) {
            for (DiningReservation reservation : reservations) {
                if (reservation.getLocation().equalsIgnoreCase(location) && reservation.getReservationTime().equals(time)) {
                    return true;
                }
            }
        }
        return false;
    }

}
