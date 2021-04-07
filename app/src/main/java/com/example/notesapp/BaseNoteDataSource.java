package com.example.notesapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public abstract class BaseNoteDataSource implements NoteDataSource {
    private HashSet<NoteDataSourceListener> mListener = new HashSet<>();
    protected final List<Note> mData = new ArrayList<>();

    @Override
    public void add(@NonNull Note note) {
        mData.add(note);
        int idx = mData.size() - 1;
        for (NoteDataSourceListener listener : mListener) {
            listener.onItemAdded(idx);
        }
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
        for (NoteDataSourceListener listener : mListener) {
            listener.onItemRemoved(position);
        }
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public List<Note> getNotes() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }

    @Override
    public Note getItemAt(int idx) {
        return mData.get(idx);
    }

    public void addNoteDataSourceListener(NoteDataSourceListener listener) {
        mListener.add(listener);
    }

    public void removeNoteDataSourceListener(NoteDataSourceListener listener) {
        mListener.remove(listener);

    }

    @Override
    public void update(int position, Note note) {
        String id = note.getId();
        if (id != null) {
            int idx = 0;
            for (Note n : mData) {
                if (id.equals(n.getId())) {
                    n.setName(note.getName());
                    n.setDescription(note.getDescription());
                    n.setName(note.getDate());
                    notifyUpdated(idx);
                    return;
                }
                idx++;
            }
        }
        add(note);
    }

    protected final void notifyUpdated(int idx) {
        for (NoteDataSourceListener listener : mListener) {
            listener.onItemUpdated(idx);
        }
    }

    protected final void notifyDataSetChanged() {
        for (NoteDataSourceListener listener : mListener) {
            listener.onDataSetChanged();
        }
    }


}
