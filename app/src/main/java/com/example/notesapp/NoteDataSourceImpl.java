package com.example.notesapp;

import android.content.res.Resources;

import java.util.Date;

public class NoteDataSourceImpl extends BaseNoteDataSource {
    private volatile static NoteDataSourceImpl sInstance;


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

}
