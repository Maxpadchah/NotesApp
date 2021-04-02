package com.example.notesapp;


public class Note {
    private String name;
    private String description;
    private String date;
    private String mId;

//    public Note() {
//    }

    public Note(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
    public String getId(){
        return mId;
    }
    public void setId(String id){
        mId = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}