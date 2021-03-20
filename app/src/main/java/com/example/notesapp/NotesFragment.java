package com.example.notesapp;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {
    private static final String ARG_TEXT_IND = NotesFragment.class.getName() + "arg.text.ind";
    private int mTextInd;


    public NotesFragment() {
    }
    public static NotesFragment newInstance(int mTextInd) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT_IND, mTextInd);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextInd = getArguments().getInt(ARG_TEXT_IND, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TypedArray textArray = getResources().obtainTypedArray(R.array.text_notes);
        TextView textView = view.findViewById(R.id.notes_text);
        textView.setText(textArray.getResourceId(mTextInd, -1));
        textArray.recycle();
    }
}