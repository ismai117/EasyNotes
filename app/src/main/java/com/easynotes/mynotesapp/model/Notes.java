package com.easynotes.mynotesapp.model;

public class Notes {
    String note_title;
    String notes;

    public Notes() {
    }

    public Notes(String note_title, String notes) {
        this.note_title = note_title;
        this.notes = notes;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
