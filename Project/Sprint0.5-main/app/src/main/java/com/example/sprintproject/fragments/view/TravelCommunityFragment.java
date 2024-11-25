package com.example.sprintproject.fragments.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.TravelCommunityViewModel;
import com.example.sprintproject.model.TravelPost;

import java.util.ArrayList;

public class TravelCommunityFragment extends Fragment {

    private TravelCommunityViewModel viewModel;
    private TravelPostAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_community, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPosts);
        Button addPostButton = view.findViewById(R.id.btnAddPost);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TravelPostAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(TravelCommunityViewModel.class);

        // Observe posts
        viewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            adapter.setPosts(posts);
            adapter.notifyDataSetChanged();
        });

        // Add new post
        addPostButton.setOnClickListener(v -> showAddPostDialog());

        // Load existing posts
        viewModel.loadPosts();

        return view;
    }

    private void showAddPostDialog() {
        AddPostDialog dialog = new AddPostDialog();
        dialog.setPostListener(post -> {
            viewModel.addTravelPost(post, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Post added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TravelCommunity", "Failed to add post: " + task.getException().getMessage());
                    Toast.makeText(getContext(), "Failed to add post.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        dialog.show(getParentFragmentManager(), "AddPostDialog");
    }
}
