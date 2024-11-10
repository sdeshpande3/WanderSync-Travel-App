package com.example.sprintproject.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByTime implements SortStrategy {
    @Override
    public List<DiningReservation> sort(List<DiningReservation> reservations) {
        Collections.sort(reservations, Comparator.comparing(DiningReservation::getReservationTime));
        return reservations;
    }
}
