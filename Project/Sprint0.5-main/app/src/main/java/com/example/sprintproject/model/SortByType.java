package com.example.sprintproject.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


//sort stategy by type
public class SortByType implements SortStrategy {
    @Override
    public List<DiningReservation> sort(List<DiningReservation> reservations) {
        Collections.sort(reservations, Comparator.comparing(DiningReservation::getLocation)); // Assuming type is based on location
        return reservations;
    }
}
