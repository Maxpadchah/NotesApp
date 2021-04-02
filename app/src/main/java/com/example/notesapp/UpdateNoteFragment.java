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

public class UpdateNoteFragment extends Fragment {


    private static final String ARG_PARAM1 = UpdateNoteFragment.class.getName() + "position";
    NoteDataSource mNoteDataSource;
    private int position;
    private TextView mTvDate;


    public UpdateNoteFragment() {
    }


    public static UpdateNoteFragment newInstance(int position) {
        UpdateNoteFragment fragment = new UpdateNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mNoteDataSource = NoteDateSourceFirebaceImpl.getInstance();
        Note note = mNoteDataSource.getItemAt(position);
        TextView tvName = view.findViewById(R.id.tvNoteName);
        tvName.setText(note.getName());
        TextView tvDescription = view.findViewById(R.id.tvNoteDescription);
        tvDescription.setText(note.getDescription());

        MaterialButton updateBtn = view.findViewById(R.id.update);

        updateBtn.setOnClickListener((v) -> {
            String name = tvName.getText().toString();
            String description = tvDescription.getText().toString();
            if (!checkFields(name, description)) return;

            NoteDataSource data = NoteDateSourceFirebaceImpl.getInstance();
            data.update(position, new Note(name, description, NoteDataSourceImpl.date()));

            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        });
        return view;
    }

    private boolean checkFields(String name, String description) {
        if (name.isEmpty() || description.isEmpty() ) {
            Toast.makeText(getActivity(), getResources().getString(R.string.message_if_empty_fields), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
