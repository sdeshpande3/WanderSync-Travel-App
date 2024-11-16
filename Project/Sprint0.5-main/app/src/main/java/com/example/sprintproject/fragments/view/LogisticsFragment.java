package com.example.sprintproject.fragments.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.LogisticsViewModel;
import com.example.sprintproject.model.Contributor;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LogisticsFragment extends Fragment {

    private static final String TAG = "LogisticsFragment";

    private LogisticsViewModel viewModel;
    private String currentTripID;
    private String currentUserID;

    private ListView contributorsListView;
    private Button inviteButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        contributorsListView = view.findViewById(R.id.contributorsListView);
        inviteButton = view.findViewById(R.id.inviteButton);

        viewModel = new ViewModelProvider(this).get(LogisticsViewModel.class);

        inviteButton.setOnClickListener(v -> showInviteDialog());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (currentUserID == null) {
            Toast.makeText(getContext(), "User not logged in.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Current user ID is null.");
            return view;
        }

        // Replace with actual trip ID fetching logic
        currentTripID = "your_trip_id";

        if (currentTripID == null) {
            Toast.makeText(getContext(), "No active trip found.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Current trip ID is null.");
        } else {
            loadContributors();  // Load contributors when the trip ID is valid
        }

        return view;
    }

    private void showInviteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Invite User");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint("Enter user's email");
        builder.setView(input);

        builder.setPositiveButton("Invite", (dialog, which) -> {
            String emailToInvite = input.getText().toString().trim();
            if (!emailToInvite.isEmpty()) {
                addContributor(emailToInvite);
            } else {
                Toast.makeText(getContext(), "Please enter an email.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void addContributor(String email) {
        if (currentTripID == null || currentTripID.isEmpty()) {
            Toast.makeText(getContext(), "No active trip found. Cannot add contributor.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "addContributor: currentTripID is null or empty.");
            return;
        }

        Contributor newContributor = new Contributor(email, true);

        viewModel.checkContributorExists(currentTripID, email, exists -> {
            if (exists) {
                Toast.makeText(getContext(), "Contributor already exists.", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Contributor with email " + email + " already exists for trip: " + currentTripID);
            } else {
                viewModel.addContributor(currentTripID, newContributor, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "User invited successfully.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Contributor added: " + email);
                        loadContributors();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred.";
                        Toast.makeText(getContext(), "Failed to invite user: " + errorMessage, Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Failed to invite user: " + errorMessage, task.getException());
                    }
                });
            }
        });
    }

    private void loadContributors() {
        if (currentTripID == null) {
            Log.e(TAG, "Current trip ID is null. Cannot load contributors.");
            return;
        }

        viewModel.getContributors(currentTripID, new LogisticsViewModel.ContributorsCallback() {
            @Override
            public void onSuccess(ArrayList<Contributor> contributors) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        contributors.stream().map(Contributor::getEmail).collect(Collectors.toList())
                );
                contributorsListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Failed to load contributors: " + error, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Failed to load contributors: " + error);
            }
        });
    }
}
