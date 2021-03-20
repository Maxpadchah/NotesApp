package com.example.notesapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NameListFragment extends Fragment {
    private int mCurrentTextIdx = -1;
    private static final String ARG_TEXT_IND = NameListFragment.class.getName() + "arg.text.ind";

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
        String[] namesNotes = getResources().getStringArray(R.array.name_notes);
        int idx = 0;
        for (String notesName : namesNotes) {
            TextView tv = new TextView(getContext());
            tv.setText(notesName);
            tv.setTextSize(35);
            final int idxImage = idx;
            tv.setOnClickListener((v) -> {
                setCurrentTextIdx(idxImage);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    goToSeparateActivity(idxImage);
                } else {
                    showRight(idxImage);
                }
            });
            view.addView(tv);
            idx++;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentTextIdx = savedInstanceState.getInt(NameListFragment.ARG_TEXT_IND, -1);
            if (mCurrentTextIdx != -1 && getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                showRight(mCurrentTextIdx);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NameListFragment.ARG_TEXT_IND, mCurrentTextIdx);
    }

    private void setCurrentTextIdx(int idxImage) {
        mCurrentTextIdx = idxImage;
    }

    private void goToSeparateActivity(int idxImage) {
        Intent intent = new Intent(getActivity(), TextActivity.class);
        intent.putExtra(TextActivity.KEY_INDEX_TEXT, idxImage);
        startActivity(intent);
    }

    private void showRight(int idxImage) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_text_container, NotesFragment.newInstance(idxImage));
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}