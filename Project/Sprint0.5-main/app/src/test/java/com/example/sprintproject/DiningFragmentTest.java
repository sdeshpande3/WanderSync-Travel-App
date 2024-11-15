package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.fragments.view.DiningFragment;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.model.SortByTime;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiningFragmentTest {

    private DiningFragment fragment;

    @Before
    public void setUp() {
        fragment = new DiningFragment();
    }

    @Test
    public void testSortByTime() {
        List<DiningReservation> reservations = new ArrayList<>();
        reservations.add(new DiningReservation("Place A", "http://a.com", 4.0f, "15:00"));
        reservations.add(new DiningReservation("Place B", "http://b.com", 3.5f, "13:00"));

        SortByTime sortByTime = new SortByTime();
        List<DiningReservation> sortedReservations = sortByTime.sort(reservations);

        assertEquals("13:00", sortedReservations.get(0).getReservationTime());
        assertEquals("15:00", sortedReservations.get(1).getReservationTime());
    }

    @Test
    public void testValidateReservation_EmptyFields() {
        boolean isValid = fragment.validateReservationInput("", "", "", 0);
        assertFalse("Expected false for empty inputs", isValid);
    }

    @Test
    public void testValidateReservation_ValidFields() {
        boolean isValid = fragment.validateReservationInput("Restaurant", "http://example.com", "12:00", 4.5f);
        assertTrue("Expected true for valid inputs", isValid);
    }
}
