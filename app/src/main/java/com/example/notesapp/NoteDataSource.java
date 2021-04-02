package com.example.notesapp;

import androidx.annotation.NonNull;

import java.util.List;

public interface NoteDataSource {
    interface NoteDataSourceListener{
        void onItemAdded(int idx);
        void onItemRemoved(int idx);
        void onItemUpdated(int idx);
        void onDataSetChanged();
    }

    void addNoteDataSourceListener(NoteDataSourceListener listener);
    void removeNoteDataSourceListener(NoteDataSourceListener listener);
    List<Note> getNotes();

    int getItemsCount();

    Note getItemAt(int idx);

    void update(int position, Note note);

    void add(@NonNull Note note);

    void remove(int position);

    void clear();

}