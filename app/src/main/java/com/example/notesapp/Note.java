package com.example.notesapp;

import java.util.Date;

public class Note {
    private String nameNotes;
    private String textNotes;
    private String dateNotes;

    public Note() {

    }

    public Note(String nameNotes, String textNotes, String dateNotes) {
        this.nameNotes = nameNotes;
        this.textNotes = textNotes;
        this.dateNotes = dateNotes;
    }

    public String getNameNotes() {
        nameNotes = String.format(String.valueOf(R.array.name_notes));
        return nameNotes;
    }

    public String getTextNotes() {
        return textNotes;
    }

    public String getDateNotes() {
        dateNotes = date();
        return dateNotes;
    }


    public static String date() {
        Date date = new Date();
        String dateResult = "\n" + date.toString();
        return dateResult;
    }
}
