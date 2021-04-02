package com.example.notesapp;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NoteDataSourceImpl implements NoteDataSource {
    private volatile static NoteDataSourceImpl sInstance;
    private final List<Note> mData = new ArrayList<>();

    public static NoteDataSourceImpl getInstance(Resources resources) {
        NoteDataSourceImpl instance = sInstance;
        if (instance == null) {
            synchronized (NoteDataSourceImpl.class) {
                if (sInstance == null) {
                    instance = new NoteDataSourceImpl(resources);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }


    private NoteDataSourceImpl(Resources resources) {
        String[] noteNames = resources.getStringArray(R.array.names);
        String[] noteDescriptions = resources.getStringArray(R.array.descriptions);
        for (int i = 0; i < noteNames.length; i++) {
            mData.add(new Note(noteNames[i], noteDescriptions[i], date()));
        }
    }

    public static String date() {
        Date date = new Date();
        String dateResult = "\n" + date.toString();
        return dateResult;
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

    @Override
    public void update(int position, Note note) {
        mData.set(position, note);
    }

    @Override
    public void add(@NonNull Note note) {
        mData.add(note);
    }

    @Override
    public void remove(int position) {
        mData.remove(position);
    }

    @Override
    public void clear() {
        mData.clear();
    }
}
