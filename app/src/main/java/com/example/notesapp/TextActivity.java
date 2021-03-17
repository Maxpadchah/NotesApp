package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class TextActivity extends AppCompatActivity {
    public static String KEY_INDEX_TEXT = TextActivity.class.getName() + "key.index.text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        if (savedInstanceState == null){
            int textIdx = getIntent().getIntExtra(KEY_INDEX_TEXT, -1);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.notes_text_container, NotesFragment.newInstance(textIdx));
            transaction.commit();
        }
    }
}