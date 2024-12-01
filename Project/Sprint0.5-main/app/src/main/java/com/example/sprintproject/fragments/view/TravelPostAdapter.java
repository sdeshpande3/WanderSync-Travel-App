package com.example.sprintproject.fragments.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelPost;

import java.util.List;
import java.util.stream.Collectors;

public class TravelPostAdapter extends
        RecyclerView.Adapter<TravelPostAdapter.TravelPostViewHolder> {

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

        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    private String formatList(List<?> list) {
        if (list == null || list.isEmpty()) {
            return "None";
        }
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    static class TravelPostViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final TextView tripDuration;
        private final TextView transportation;
        private final TextView notes;
        private final TextView destinations;
        private final TextView dining;
        private final TextView accommodations;
        private final TextView userEmail;

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

        public void bind(TravelPost post) {
            userName.setText("User Name: " + post.getUserName());
            tripDuration.setText("Duration: " + post.getTripDuration());
            destinations.setText("Destinations: " + formatList(post.getDestinations()));
            dining.setText("Dining: " + formatList(post.getDiningReservations()));
            accommodations.setText("Accommodations: " + formatList(post.getAccommodations()));
            notes.setText("Notes: " + post.getNotes());
            userEmail.setText("User Email: " + post.getUserId());
            transportation.setText("Transportation: " + post.getTransportation());
        }

        private String formatList(List<?> list) {
            if (list == null || list.isEmpty()) {
                return "None";
            }
            return list.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }
    }
}
