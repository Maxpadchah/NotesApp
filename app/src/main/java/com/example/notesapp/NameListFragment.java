package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NameListFragment extends Fragment {
    public NameListFragment() {
    }


    public static NameListFragment newInstance() {
        NameListFragment fragment = new NameListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_name_list, container, false);
        String[] notesNames = getResources().getStringArray(R.array.name_notes);
        int idx = 0;
        for (String notesName : notesNames) {
            TextView tv = new TextView(getContext());
            tv.setText(notesName);
            tv.setTextSize(35);
            tv.setOnClickListener((v) -> {
                Intent intent = new Intent(getActivity(), TextActivity.class);
                intent.putExtra(TextActivity.KEY_INDEX_TEXT, idx);
                startActivity(intent);
            });
            view.addView(tv);
        }
        return view;
    }
}