package com.example.sprintproject.fragments.view;


public abstract class TravelPostDecorator implements TravelPostFormatter {
    protected TravelPostFormatter travelPost;

    public TravelPostDecorator(TravelPostFormatter travelPost) {
        this.travelPost = travelPost;
    }

    @Override
    public String format() {
        return travelPost.format();
    }
}