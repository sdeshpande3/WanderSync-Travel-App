package com.example.sprintproject.fragments.view;

public class TransportationDecorator extends TravelPostDecorator {
    private String transportation;

    public TransportationDecorator(TravelPostFormatter travelPost, String transportation) {
        super(travelPost);
        this.transportation = transportation;
    }

    @Override
    public String format() {
        return super.format() + "Transportation: " + transportation + "\n";
    }
}
