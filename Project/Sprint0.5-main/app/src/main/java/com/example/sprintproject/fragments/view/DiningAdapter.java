package com.example.sprintproject.fragments.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DiningReservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiningReservation reservation = reservations.get(position);
        holder.setLocation(reservation.getLocation());
        holder.setWebsite(reservation.getWebsite());
        holder.setReservationTime(reservation.getReservationTime());
        holder.setReview(reservation.getReview());

        // Apply visual indication based on whether the reservation is upcoming or expired
        boolean isUpcoming = isReservationUpcoming(reservation.getReservationTime());
        if (isUpcoming) {
            holder.itemView.setBackgroundColor(
                    Color.parseColor("#DFFFD6")); // Light green for upcoming
        } else {
            holder.itemView.setBackgroundColor(
                    Color.parseColor("#FFD6D6")); // Light red for expired
        }
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    private boolean isReservationUpcoming(String reservationTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            // Get current time as a string
            String currentTimeString = sdf.format(new Date());
            Date currentTime = sdf.parse(currentTimeString);
            Date reservationDate = sdf.parse(reservationTime);

            // Compare times on the same day
            return reservationDate != null && reservationDate.after(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView location;
        private TextView website;
        private TextView reservationTime;
        private RatingBar review;

        ViewHolder(View itemView) {
            super(itemView);
            this.location = itemView.findViewById(R.id.textLocation);
            this.website = itemView.findViewById(R.id.textWebsite);
            this.reservationTime = itemView.findViewById(R.id.textReservationTime);
            this.review = itemView.findViewById(R.id.ratingBarReview);
        }

        public void setLocation(String location) {
            this.location.setText(location);
        }

        public void setWebsite(String website) {
            this.website.setText(website);
        }

        public void setReservationTime(String reservationTime) {
            this.reservationTime.setText(reservationTime);
        }

        public void setReview(float review) {
            this.review.setRating(review);
        }
    }
}
