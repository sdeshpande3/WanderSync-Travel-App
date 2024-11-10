package com.example.sprintproject.model;

import java.util.List;

public interface SortStrategy {
    List<DiningReservation> sort(List<DiningReservation> reservations);
}
