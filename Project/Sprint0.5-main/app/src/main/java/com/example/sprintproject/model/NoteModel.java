package com.example.sprintproject.model;


public class NoteModel {
    private String note;
    private long timestamp;

    public NoteModel() {
        // Required empty constructor
    }

    public NoteModel(String note, long timestamp) {
        this.note = note;
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
