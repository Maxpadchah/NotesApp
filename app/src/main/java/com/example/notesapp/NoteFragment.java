package com.example.notesapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class NoteFragment extends Fragment {
    private static final String ARG_PARAM = NoteFragment.class.getName() + "position";
    private Note note;
    private int position;
    NoteDataSource data;

    public NoteFragment() {

    }

    public static NoteFragment newInstance(int position) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_note, container, false);
        TextView tvName = view.findViewById(R.id.tvNoteName);
        data = NoteDataSourceImpl.getInstance(getResources());
        if (data.getItemAt(position) != null) {
            note = data.getItemAt(position);
            tvName.setText(note.getName());
            TextView tvDescription = view.findViewById(R.id.tvNoteDescription);
            tvDescription.setText(note.getDescription());
        }
        return view;
    }
}