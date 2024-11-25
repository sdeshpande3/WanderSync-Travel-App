package com.example.sprintproject.fragments.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelPost;

import java.util.stream.Collectors;

import java.util.List;

public class TravelPostAdapter extends RecyclerView.Adapter<TravelPostAdapter.TravelPostViewHolder> {

    private List<TravelPost> posts;

    public TravelPostAdapter(List<TravelPost> posts) {
        this.posts = posts;
    }

    public void setPosts(List<TravelPost> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public TravelPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_travel_post, parent, false);
        return new TravelPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelPostViewHolder holder, int position) {
        TravelPost post = posts.get(position);

        holder.userName.setText("User Name: " + post.getUserName());
        holder.tripDuration.setText("Duration: " + post.getTripDuration());
        holder.destinations.setText("Destinations: " + formatList(post.getDestinations()));
        holder.dining.setText("Dining: " + formatList(post.getDiningReservations()));
        holder.accommodations.setText("Accommodations: " + formatList(post.getAccommodations()));
        holder.notes.setText("Notes: " + post.getNotes());
        holder.userEmail.setText("User Email: " + post.getUserId());
        holder.transportation.setText("Transportation: " + post.getTransportation());
    }

    private String formatList(List<?> list) {
        if (list == null || list.isEmpty()) return "None";
        return list.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class TravelPostViewHolder extends RecyclerView.ViewHolder {
        TextView userName, tripDuration, transportation, notes, destinations, dining, accommodations, userEmail;

        public TravelPostViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tvUserName);
            tripDuration = itemView.findViewById(R.id.tvTripDuration);
            transportation = itemView.findViewById(R.id.tvTransportation);
            notes = itemView.findViewById(R.id.tvNotes);
            destinations = itemView.findViewById(R.id.tvDestinations);
            dining = itemView.findViewById(R.id.tvDining);
            accommodations = itemView.findViewById(R.id.tvAccommodations);
            userEmail = itemView.findViewById(R.id.tvUserEmail);
        }
    }
}
