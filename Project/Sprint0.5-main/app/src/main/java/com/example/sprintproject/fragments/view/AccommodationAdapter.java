package com.example.sprintproject.fragments.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sprintproject.R;
import com.example.sprintproject.fragments.viewmodel.AccommodationViewModel;
import com.example.sprintproject.model.Accommodation;

import java.util.List;

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
}
