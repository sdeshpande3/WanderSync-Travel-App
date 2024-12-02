package com.example.sprintproject.fragments.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.AccommodationViewModel;
import com.example.sprintproject.model.Accommodation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccommodationFragment extends Fragment {

    private AccommodationViewModel accommodationViewModel;
    private RecyclerView recyclerView;
    private AccommodationAdapter adapter;

    public AccommodationFragment() {
        // Required empty public constructor
    }

    public static AccommodationFragment newInstance() {
        return new AccommodationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation, container, false);

        accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewAccommodations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AccommodationAdapter(new ArrayList<>(), accommodationViewModel);
        recyclerView.setAdapter(adapter);

        accommodationViewModel.getAccommodations().observe(
                getViewLifecycleOwner(),
                accommodations -> adapter.updateData(accommodations)
        );

        // Load data from Firebase Firestore
        accommodationViewModel.loadAccommodations();

        View fab = view.findViewById(R.id.fabAddAccommodation);
        fab.setOnClickListener(v -> showAddAccommodationDialog());

        return view;
    }

    private void showAddAccommodationDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_accommodation, null);

        dialog.setContentView(dialogView);

        EditText locationField = dialogView.findViewById(R.id.editTextLocation);
        EditText checkInField = dialogView.findViewById(R.id.editTextCheckInDate);
        EditText checkOutField = dialogView.findViewById(R.id.editTextCheckOutDate);
        EditText roomsField = dialogView.findViewById(R.id.editTextRooms);
        Spinner roomTypeSpinner = dialogView.findViewById(R.id.spinnerRoomType);
        Button saveButton = dialogView.findViewById(R.id.buttonSaveAccommodation);

        // DatePicker for Check-In
        checkInField.setOnClickListener(v -> showDatePickerDialog(checkInField));

        // DatePicker for Check-Out
        checkOutField.setOnClickListener(v -> showDatePickerDialog(checkOutField));

        saveButton.setOnClickListener(v -> {
            String location = locationField.getText().toString().trim();
            String checkInDate = checkInField.getText().toString().trim();
            String checkOutDate = checkOutField.getText().toString().trim();
            String roomsText = roomsField.getText().toString().trim();
            String roomType = roomTypeSpinner.getSelectedItem().toString();

            if (location.isEmpty()
                    || checkInDate.isEmpty()
                    || checkOutDate.isEmpty()
                    || roomsText.isEmpty() || roomType.isEmpty()) {
                Toast.makeText(getContext(),
                        "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            int numberOfRooms = Integer.parseInt(roomsText);

            if (isDuplicateAccommodation(location, checkInDate, checkOutDate)) {
                Toast.makeText(
                        getContext(),
                        "Accommodation with overlapping dates at this location already exists.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Accommodation accommodation = new Accommodation(
                    location, checkInDate, checkOutDate, numberOfRooms, roomType
            );

            accommodationViewModel.addAccommodation(accommodation);

            Toast.makeText(
                    getContext(),
                    "Accommodation added successfully!",
                    Toast.LENGTH_SHORT
            ).show();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showDatePickerDialog(EditText dateField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedYear + "-" + (selectedMonth + 1)
                            + "-" + selectedDay;
                    dateField.setText(formattedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private boolean isDuplicateAccommodation(String location,
                                             String checkInDate, String checkOutDate) {
        List<Accommodation> accommodations = accommodationViewModel.getAccommodations().getValue();
        if (accommodations != null) {
            for (Accommodation existing : accommodations) {
                if (existing.getLocation().equalsIgnoreCase(location)
                        && areDatesOverlapping(
                                existing.getCheckInDate(),
                                existing.getCheckOutDate(),
                                checkInDate,
                                checkOutDate
                        )) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean areDatesOverlapping(
            String existingCheckIn,
            String existingCheckOut,
            String newCheckIn,
            String newCheckOut
    ) {
        return !(newCheckOut.compareTo(existingCheckIn) < 0
                || newCheckIn.compareTo(existingCheckOut) > 0);
    }
}
