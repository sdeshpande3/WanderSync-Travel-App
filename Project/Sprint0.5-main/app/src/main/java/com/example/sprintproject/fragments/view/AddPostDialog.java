package com.example.sprintproject.fragments.view;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.model.DestinationDatabase.TravelLog;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.model.DestinationDatabase;
import com.example.sprintproject.fragments.viewmodel.DiningViewModel;

import com.example.sprintproject.fragments.view.TravelPostFactory;

import com.google.firebase.auth.FirebaseAuth;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.fragments.viewmodel.AccommodationViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddPostDialog extends DialogFragment {

    private PostListener postListener;
    private List<TravelLog> selectedDestinations = new ArrayList<>();
    private List<DiningReservation> selectedDining = new ArrayList<>();
    private List<Accommodation> selectedAccommodations = new ArrayList<>();

    private DiningViewModel diningViewModel;
    private AccommodationViewModel accommodationViewModel;

    public interface PostListener {
        void onPostCreated(TravelPost post);
    }

    public void setPostListener(PostListener listener) {
        this.postListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_post);

        EditText tripDurationInput = dialog.findViewById(R.id.tripDurationInput);
        EditText notesInput = dialog.findViewById(R.id.notesInput);
        Button btnSavePost = dialog.findViewById(R.id.btnSavePost);
        EditText transportationInput = dialog.findViewById(R.id.transportationInput);

        Button btnSelectDestinations = dialog.findViewById(R.id.btnSelectDestinations);
        Button btnSelectDining = dialog.findViewById(R.id.btnSelectDining);
        Button btnSelectAccommodations = dialog.findViewById(R.id.btnSelectAccommodations);

        // Initialize ViewModels
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);
        accommodationViewModel = new ViewModelProvider(this).get(AccommodationViewModel.class);

        // Load data for all types in advance
        preloadData();

        btnSelectDestinations.setOnClickListener(v -> {
            List<TravelLog> userDestinations = getUserDestinations();
            if (userDestinations.isEmpty()) {
                showEmptyMessage("No destinations found.");
                return;
            }
            GenericSelectionDialog<TravelLog> genericDialog = new GenericSelectionDialog<>(
                    userDestinations,
                    "Select a Destination",
                    selectedItem -> selectedDestinations.add(selectedItem)
            );
            genericDialog.show(getParentFragmentManager(), "DestinationSelectionDialog");
        });

        btnSelectDining.setOnClickListener(v -> {
            List<DiningReservation> userDining = getUserDiningReservations();
            if (userDining.isEmpty()) {
                showEmptyMessage("No dining reservations found.");
                return;
            }
            GenericSelectionDialog<DiningReservation> genericDialog = new GenericSelectionDialog<>(
                    userDining,
                    "Select a Dining Reservation",
                    selectedItem -> selectedDining.add(selectedItem)
            );
            genericDialog.show(getParentFragmentManager(), "DiningSelectionDialog");
        });

        btnSelectAccommodations.setOnClickListener(v -> {
            List<Accommodation> userAccommodations = getUserAccommodations();
            if (userAccommodations.isEmpty()) {
                showEmptyMessage("No accommodations found.");
                return;
            }
            GenericSelectionDialog<Accommodation> genericDialog = new GenericSelectionDialog<>(
                    userAccommodations,
                    "Select an Accommodation",
                    selectedItem -> selectedAccommodations.add(selectedItem)
            );
            genericDialog.show(getParentFragmentManager(), "AccommodationSelectionDialog");
        });

        String userEmail = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getEmail()
                : "unknown@example.com";

        btnSavePost.setOnClickListener(v -> {
            String tripDuration = tripDurationInput.getText().toString().trim();
            String notes = notesInput.getText().toString().trim();
            String transportation = transportationInput.getText().toString().trim();

            TravelPost post = TravelPostFactory.createPost(
                    userEmail,
                    userEmail,
                    tripDuration,
                    selectedDestinations,
                    selectedAccommodations,
                    selectedDining,
                    transportation,
                    notes
            );

            TravelPostFormatter decoratedPost = new TransportationDecorator(
                    new NotesDecorator(post, post.getNotes()),
                    post.getTransportation()
            );

            System.out.println(decoratedPost.format());

            if (postListener != null) {
                postListener.onPostCreated(post);
            }
            dismiss();
        });

        return dialog;
    }

    private void preloadData() {
        // Preload data to avoid delay when opening dialogs
        diningViewModel.loadReservations();
        accommodationViewModel.loadAccommodations();
        DestinationDatabase.getInstance().getTravelLogs(); // Ensure data is fetched
    }

    private List<DestinationDatabase.TravelLog> getUserDestinations() {
        List<DestinationDatabase.TravelLog> travelLogs = DestinationDatabase.getInstance()
                .getTravelLogs()
                .getValue();
        return travelLogs != null ? travelLogs : new ArrayList<>();
    }

    private List<DiningReservation> getUserDiningReservations() {
        List<DiningReservation> reservations = diningViewModel.getReservations().getValue();
        return reservations != null ? reservations : new ArrayList<>();
    }

    private List<Accommodation> getUserAccommodations() {
        List<Accommodation> accommodations = accommodationViewModel.getAccommodations().getValue();
        return accommodations != null ? accommodations : new ArrayList<>();
    }

    private void showEmptyMessage(String message) {
        // Display a toast message for empty lists
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
