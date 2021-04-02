package com.example.notesapp;

import androidx.annotation.NonNull;

import java.util.List;

public interface NoteDataSource {
    List<Note> getNotes();

    int getItemsCount();

    Note getItemAt(int idx);

    void update(int position, Note note);

    void add(@NonNull Note note);

    void remove(int position);

    void clear();

}