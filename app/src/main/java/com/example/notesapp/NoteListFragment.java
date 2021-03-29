package com.example.notesapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NoteListFragment extends Fragment {

    private List<Note> notes = new ArrayList();
    private int position;
    private Note note;
    private static final String ARG_PARAM1 = "note";
    private static final String  ARG_PARAM2 = "position";



    public static com.example.notesapp.NoteListFragment newInstance(Note param1, int position) {
        com.example.notesapp.NoteListFragment fragment = new com.example.notesapp.NoteListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInitialData();
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
            notes.set(position, note);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        com.example.notesapp.NoteAdapter.OnNoteClickListener onNoteClickListener = ((Note note, int position) -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                changeFragment(note, position);
            }else {
                showToTheRight(note, position);
            }
        });

        com.example.notesapp.NoteAdapter adapter = new com.example.notesapp.NoteAdapter(getActivity(), notes, onNoteClickListener);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void showToTheRight(Note note, int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_container, com.example.notesapp.NoteFragment.newInstance(note, position));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void changeFragment(Note note, int position){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, com.example.notesapp.NoteFragment.newInstance(note, position));
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();

    }


    private void setInitialData(){
        notes.add(new Note("Заметка 1", "Текст заметки 1", date()));
        notes.add(new Note("Заметка 2", "Текст заметки 2", date()));
        notes.add(new Note ("Заметка 3", "Текст заметки 3", date()));

    }

    public static String date() {
        Date date = new Date();
        String dateResult = "\n" + date.toString();
        return dateResult;
    }
}