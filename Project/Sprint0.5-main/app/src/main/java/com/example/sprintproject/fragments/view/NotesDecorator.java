package com.example.sprintproject.fragments.view;

public class NotesDecorator extends TravelPostDecorator {
    private String notes;

    public NotesDecorator(TravelPostFormatter travelPost, String notes) {
        super(travelPost);
        this.notes = notes;
    }

    @Override
    public String format() {
        return super.format() + "Notes: " + notes + "\n";
    }
}
