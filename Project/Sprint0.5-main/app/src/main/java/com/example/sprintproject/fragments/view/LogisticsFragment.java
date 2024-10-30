package com.example.sprintproject.fragments.view;

import java.util.ArrayList;
import java.util.HashMap;


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

import com.example.sprintproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class LogisticsFragment extends Fragment {

    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private String currentUserEmail;
    private String currentTripID;
    private ListView contributorsListView, notesListView;
    private Button inviteButton, addTripButton, addNoteButton;
    private EditText noteEditText;


    private static final String TAG = "LogisticsFragment"; // Tag for logging


    public LogisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FirebaseAuth and FirebaseDatabase references
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        // Get current user ID from FirebaseAuth
        if (mAuth.getCurrentUser() != null) {
            currentUserID = mAuth.getCurrentUser().getUid();
        } else {
            Toast.makeText(getContext(), "User is not logged in.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "User is not logged in.");
            return;
        }

        // Fetch the current user's email from the Realtime Database
        databaseRef.child("Users").child(currentUserID).child("email")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            currentUserEmail = snapshot.getValue(String.class);
                            Log.d(TAG, "Retrieved email from database: " + currentUserEmail);
                            // Proceed to load the user's trip
                            loadUserTrip();
                        } else {
                            Toast.makeText(getContext(), "Email not found for the current user.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Email not found for the current user in database.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed to retrieve email from database.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to retrieve email from database: " + error.getMessage());
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        contributorsListView = view.findViewById(R.id.contributorsListView);
        notesListView = view.findViewById(R.id.notesListView);
        inviteButton = view.findViewById(R.id.inviteButton);
        addTripButton = view.findViewById(R.id.addTripButton);
        addNoteButton = view.findViewById(R.id.addNoteButton);
        noteEditText = view.findViewById(R.id.noteEditText);

        inviteButton.setOnClickListener(v -> inviteUser());
        addTripButton.setOnClickListener(v -> addTrip());
        addNoteButton.setOnClickListener(v -> addNote());


        return view;
    }



    private void addNote() {
        // Create an AlertDialog to enter the new note
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Note");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter note");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String newNote = input.getText().toString().trim();
            if (!newNote.isEmpty()) {
                appendNoteToDatabase(newNote);
            } else {
                Toast.makeText(getContext(), "Please enter a note.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void appendNoteToDatabase(String newNote) {
        if (currentTripID == null) {
            Log.e(TAG, "Trip ID is null.");
            Toast.makeText(getContext(), "No trip found for the current user.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference the note field for the logged-in user
        DatabaseReference noteRef = databaseRef.child("Users").child(currentUserID).child("tripID").child("note");

        // Retrieve the current note
        noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String updatedNotes;

                if (snapshot.exists()) {
                    // Get the current note string
                    String currentNote = snapshot.getValue(String.class);

                    // Append new note with a comma separator
                    if (currentNote != null && !currentNote.isEmpty()) {
                        updatedNotes = currentNote + ", " + newNote;
                    } else {
                        updatedNotes = newNote;
                    }
                } else {
                    // If no note exists, initialize with the new note
                    updatedNotes = newNote;
                }

                // Update the note field with the new string
                noteRef.setValue(updatedNotes).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Note added successfully.", Toast.LENGTH_SHORT).show();
                        loadNotes(); // Reload notes to refresh the ListView
                    } else {
                        Toast.makeText(getContext(), "Failed to add note.", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to update note.");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load note.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load note: " + error.getMessage());
            }
        });
    }

    // Rest of your existing methods such as inviteUser, addTrip, addContributor, loadUserTrip, loadContributors, and loadNotes...



private void loadUserTrip() {
        // Fetch current user's tripID from Realtime Database
        DatabaseReference userRef = databaseRef.child("Users").child(currentUserID);
        userRef.child("tripID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Access the tripID object and get the contributors and note values
                    DataSnapshot tripSnapshot = snapshot;

                    if (tripSnapshot.child("contributors").exists()) {
                        String contributors = tripSnapshot.child("contributors").getValue(String.class);
                        Log.d(TAG, "Contributors: " + contributors);
                    }

                    if (tripSnapshot.child("note").exists()) {
                        String note = tripSnapshot.child("note").getValue(String.class);
                        Log.d(TAG, "Note: " + note);
                    }

                    // Store the current trip ID
                    currentTripID = tripSnapshot.getKey();
                    Log.d(TAG, "Current trip ID: " + currentTripID);
                    Toast.makeText(getContext(), "Trip found", Toast.LENGTH_SHORT).show();

                    // Load contributors and notes
                    loadContributors();
                    loadNotes();
                } else {
                    // No trip exists, show add trip button
                    addTripButton.setVisibility(View.VISIBLE);
                    inviteButton.setVisibility(View.GONE);
                    addNoteButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load trip ID.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load trip ID: " + error.getMessage());
            }
        });
    }

    private void addTrip() {
        // Create an AlertDialog to enter the trip name
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Create New Trip");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter trip name");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Create", (dialog, which) -> {
            String tripName = input.getText().toString().trim();
            if (!tripName.isEmpty()) {
                // Reference the trips node in Firebase Realtime Database
                DatabaseReference tripsRef = databaseRef.child("trips");

                // Generate a unique ID for the new trip
                currentTripID = tripsRef.push().getKey();

                if (currentTripID != null) {
                    // Prepare the data for the new trip
                    HashMap<String, Object> tripMap = new HashMap<>();
                    tripMap.put("tripName", tripName);

                    // Add the current user's email as an initial contributor
                    HashMap<String, Object> usersMap = new HashMap<>();
                    usersMap.put(currentUserEmail, true);
                    tripMap.put("users", usersMap);

                    // Save trip details in the database under the generated trip ID
                    tripsRef.child(currentTripID).setValue(tripMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Also add the trip ID to the user's data in the Users node
                            databaseRef.child("Users").child(currentUserID).child("tripID").setValue(currentTripID)
                                    .addOnCompleteListener(userTask -> {
                                        if (userTask.isSuccessful()) {
                                            Toast.makeText(getContext(), "Trip created successfully!", Toast.LENGTH_SHORT).show();
                                            addTripButton.setVisibility(View.GONE);
                                            inviteButton.setVisibility(View.VISIBLE);
                                            addNoteButton.setVisibility(View.VISIBLE);

                                            // Load contributors and notes after trip creation
                                            loadContributors();
                                            loadNotes();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to add trip ID to user: " + userTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Failed to create trip: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.e(TAG, "Failed to generate a new trip ID.");
                    Toast.makeText(getContext(), "Failed to create trip. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a trip name.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    private void inviteUser() {
        // Create an AlertDialog to enter the email of the user to invite
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Invite User");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        input.setHint("Enter user's email");
        builder.setView(input);

        // Set up the buttons
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

    private void addContributor(String emailToInvite) {
        if (currentTripID == null) {
            Log.e(TAG, "Trip ID is null.");
            Toast.makeText(getContext(), "No trip found for the current user.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference the contributors section for the logged-in user
        DatabaseReference contributorsRef = databaseRef.child("Users").child(currentUserID).child("tripID").child("contributors");

        // Retrieve the current contributors list
        contributorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the current value as a single string
                    String currentContributors = snapshot.getValue(String.class);

                    // Append new contributor
                    String updatedContributors;
                    if (currentContributors != null && !currentContributors.isEmpty()) {
                        // If there are already contributors, append with a comma
                        updatedContributors = currentContributors + ", " + emailToInvite;
                    } else {
                        // If no contributors exist, set the new contributor as the only entry
                        updatedContributors = emailToInvite;
                    }

                    // Update the contributors node with the new string
                    contributorsRef.setValue(updatedContributors).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Contributor added successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to add contributor.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to update contributors.");
                        }
                    });
                } else {
                    // If contributors node does not exist, initialize with the new contributor
                    contributorsRef.setValue(emailToInvite).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Contributor added successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to add contributor.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to update contributors.");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load contributors.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load contributors: " + error.getMessage());
            }
        });
    }



    private void findUserByEmail(String email) {
        DatabaseReference usersRef = databaseRef.child("Users");
        Query query = usersRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User found
                    DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                    String invitedUserID = userSnapshot.getKey();
                    Log.d(TAG, "User found with email " + email + ", userID: " + invitedUserID);
                    addUserToTrip(invitedUserID, email);
                } else {
                    Toast.makeText(getContext(), "User not found.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "User not found with email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to search for user.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to search for user by email: " + error.getMessage());
            }
        });
    }

    private void addUserToTrip(String invitedUserID, String email) {
        // Add user's email to trip's users list
        DatabaseReference tripUsersRef = databaseRef.child("trips").child(currentTripID).child("users");
        tripUsersRef.child(email).setValue(true);

        // Update invited user's tripID
        DatabaseReference invitedUserRef = databaseRef.child("Users").child(invitedUserID);
        invitedUserRef.child("tripID").setValue(currentTripID).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "User invited successfully.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "User " + invitedUserID + " invited successfully to trip " + currentTripID);
                // Reload contributors to update the list
                loadContributors();
            } else {
                Toast.makeText(getContext(), "Failed to invite user.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to invite user " + invitedUserID + " to trip " + currentTripID);
            }
        });
    }

    private void loadContributors() {
        if (currentTripID == null) {
            Log.e(TAG, "Trip ID is null.");
            Toast.makeText(getContext(), "No trip found for the current user.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference the contributors object under tripID
        DatabaseReference contributorsRef = databaseRef.child("Users").child(currentUserID).child("tripID").child("contributors");

        contributorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String contributor = snapshot.getValue(String.class);
                    if (contributor != null && !contributor.isEmpty()) {
                        ArrayList<String> contributorsList = new ArrayList<>();
                        contributorsList.add(contributor);

                        // Update the ListView with the list of contributors
                        ArrayAdapter<String> contributorsAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, contributorsList);
                        contributorsListView.setAdapter(contributorsAdapter);
                    } else {
                        Log.e(TAG, "Contributors field is empty for the current user's trip.");
                        Toast.makeText(getContext(), "No contributors found for the current trip.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Contributors node not found in tripID object.");
                    Toast.makeText(getContext(), "Contributors information is missing for the trip.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load contributors.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load contributors: " + error.getMessage());
            }
        });
    }

    private void loadNotes() {
        if (currentTripID == null) {
            Log.e(TAG, "Trip ID is null.");
            Toast.makeText(getContext(), "No trip found for the current user.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the user's note field under tripID
        DatabaseReference noteRef = databaseRef.child("Users").child(currentUserID).child("tripID").child("note");

        noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String note = snapshot.getValue(String.class);
                    if (note != null && !note.isEmpty()) {
                        ArrayList<String> notesList = new ArrayList<>();
                        notesList.add(note);

                        // Update the ListView with the list of notes
                        ArrayAdapter<String> notesAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, notesList);
                        notesListView.setAdapter(notesAdapter);
                    } else {
                        Log.e(TAG, "Note field is empty for the current user's trip.");
                        Toast.makeText(getContext(), "No notes found for the current trip.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Note node not found in tripID object.");
                    Toast.makeText(getContext(), "Notes information is missing for the trip.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load notes.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to load notes: " + error.getMessage());
            }
        });
    }
}