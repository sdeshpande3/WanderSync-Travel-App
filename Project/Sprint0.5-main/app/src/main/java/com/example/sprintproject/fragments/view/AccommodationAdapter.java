package com.example.sprintproject.fragments.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.AccommodationViewModel;
import com.example.sprintproject.model.Accommodation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {

    private List<Accommodation> accommodations;
    private AccommodationViewModel viewModel;

    public AccommodationAdapter(List<Accommodation> accommodations, AccommodationViewModel viewModel) {
        this.accommodations = accommodations;
        this.viewModel = viewModel;
    }

    public void updateData(List<Accommodation> newAccommodations) {
        this.accommodations = newAccommodations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accommodation accommodation = accommodations.get(position);
        holder.location.setText(accommodation.getLocation());
        holder.checkInDate.setText(accommodation.getCheckInDate());
        holder.checkOutDate.setText(accommodation.getCheckOutDate());
        holder.numberOfRooms.setText(String.valueOf(accommodation.getNumberOfRooms()));
        holder.roomType.setText(accommodation.getRoomType());

        // Apply visual indication for upcoming or expired accommodations
        boolean isUpcoming = isAccommodationUpcoming(accommodation.getCheckOutDate());
        if (isUpcoming) {
            holder.itemView.setBackgroundColor(Color.parseColor("#DFFFD6")); // Light green for upcoming
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFD6D6")); // Light red for expired
        }
    }

    @Override
    public int getItemCount() {
        return accommodations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, checkInDate, checkOutDate, numberOfRooms, roomType;

        ViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.textLocation);
            checkInDate = itemView.findViewById(R.id.textCheckInDate);
            checkOutDate = itemView.findViewById(R.id.textCheckOutDate);
            numberOfRooms = itemView.findViewById(R.id.textNumberOfRooms);
            roomType = itemView.findViewById(R.id.textRoomType);
        }
    }

    // Helper method to determine if an accommodation is upcoming
    private boolean isAccommodationUpcoming(String checkOutDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date outDate = sdf.parse(checkOutDate);
            return outDate != null && outDate.after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
