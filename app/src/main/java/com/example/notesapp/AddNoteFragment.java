package com.example.notesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

public class AddNoteFragment extends Fragment {

    private TextView mTvDate;

    public AddNoteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        TextView tvName = view.findViewById(R.id.tvNoteName);
        TextView tvDescription = view.findViewById(R.id.tvNoteDescription);
        MaterialButton addNote = view.findViewById(R.id.add);
        addNote.setOnClickListener((v) -> {
            String name = tvName.getText().toString();
            String description = tvDescription.getText().toString();
            if (!checkFields(name, description)) return;


            NoteDataSource data = NoteDateSourceFirebaceImpl.getInstance();
            Note note = new Note(name, description, NoteDataSourceImpl.date());
            data.add(note);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        });
        return view;
    }

    private boolean checkFields(String name, String description) {
        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.message_if_empty_fields), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
