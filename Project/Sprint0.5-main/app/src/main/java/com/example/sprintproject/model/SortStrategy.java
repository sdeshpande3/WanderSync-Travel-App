package com.example.sprintproject.model;

import java.util.List;


//sort stategy interface
public interface SortStrategy {
    List<DiningReservation> sort(List<DiningReservation> reservations);
}
