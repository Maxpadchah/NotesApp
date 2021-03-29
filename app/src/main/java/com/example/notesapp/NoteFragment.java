package com.example.notesapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;


public class NoteFragment extends Fragment {


    private static final String ARG_PARAM1 = NoteFragment.class.getName() + "argParam1";
    private static final String  ARG_PARAM2 = NoteFragment.class.getName() + "argParam2";
    private TextView mTvDate;
    private int position;
    private Note note;
    private Calendar dateAndTime = Calendar.getInstance();
    public NoteFragment() {

    }


    public static NoteFragment newInstance(Note param1, int position) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_note, container, false);
        TextView tvName = view.findViewById(R.id.tvNoteName);
        tvName.setText(note.getName());
        TextView tvDescription = view.findViewById(R.id.tvNoteDescription);
        tvDescription.setText(note.getDescription());
        mTvDate = view.findViewById(R.id.tvNoteDate);
        mTvDate.setText(note.getDate());
        mTvDate.setOnClickListener((v) -> {
            setDate();
        });


        return view;
    }

    public void setDate() {
        new DatePickerDialog(getActivity(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    DatePickerDialog.OnDateSetListener d = (DatePicker view, int year, int monthOfYear, int dayOfMonth) ->
    {
        monthOfYear += 1;
        String date = year + "-" + monthOfYear  + "-" + dayOfMonth;
        mTvDate.setText(date);
    };












}