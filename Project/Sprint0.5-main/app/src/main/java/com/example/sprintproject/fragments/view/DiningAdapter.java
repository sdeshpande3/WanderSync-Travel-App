package com.example.sprintproject.fragments.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sprintproject.R;
import com.example.sprintproject.model.DiningReservation;

import java.util.List;

public class DiningAdapter extends RecyclerView.Adapter<DiningAdapter.ViewHolder> {

    private List<DiningReservation> reservations;

    public DiningAdapter(List<DiningReservation> reservations) {
        this.reservations = reservations;
    }

    public void updateData(List<DiningReservation> newReservations) {
        this.reservations = newReservations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiningReservation reservation = reservations.get(position);
        holder.location.setText(reservation.getLocation());
        holder.website.setText(reservation.getWebsite());
        holder.reservationTime.setText(reservation.getReservationTime());
        holder.review.setRating(reservation.getReview()); // Bind numeric review to RatingBar
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, website, reservationTime;
        RatingBar review;

        ViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.textLocation);
            website = itemView.findViewById(R.id.textWebsite);
            reservationTime = itemView.findViewById(R.id.textReservationTime);
            review = itemView.findViewById(R.id.ratingBarReview); // Correct RatingBar reference
        }
    }


}
