package com.example.notesapp;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NoteListFragment extends Fragment {

    private NoteDataSource mDataSource;
    private int mLastSelectedPosition = -1;
    NoteAdapter mAdapter;
    private NoteDataSource.NoteDataSourceListener mListener = new NoteDataSource.NoteDataSourceListener() {
        @Override
        public void onItemAdded(int idx) {
            if (mAdapter != null) {
                mAdapter.notifyItemInserted(idx);
            }
        }

        @Override
        public void onItemRemoved(int idx) {
            if (mAdapter != null) {
                mAdapter.notifyItemRemoved(idx);
            }
        }

        @Override
        public void onItemUpdated(int idx) {
            if (mAdapter != null) {
                mAdapter.notifyItemChanged(idx);
            }

        }

        @Override
        public void onDataSetChanged() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDataSource = NoteDateSourceFirebaceImpl.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        NoteAdapter.OnNoteClickListener onNoteClickListener = ((Note note, int position) -> {
            if (!checkLandScapeOrientation()) {
                changeToNoteFragment(position);
            } else {
                showToTheRight(position);
            }
        });
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener((v) -> {
            AddDialogFragment addDialogFragment = new AddDialogFragment();
            addDialogFragment.setListener(((name, description, date) -> {
                mDataSource.add(new Note(name, description, date));
                mAdapter.notifyDataSetChanged();
            }));
            addDialogFragment.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.add_fragment));
        });


        mAdapter = new NoteAdapter(this, mDataSource.getNotes(), onNoteClickListener);
        mDataSource.addNoteDataSourceListener(mListener);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataSource.removeNoteDataSourceListener(mListener);
    }

    private void showToTheRight(int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_container, NoteFragment.newInstance(position));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }


    private void changeToNoteFragment(int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, NoteFragment.newInstance(position));
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.note_list_item_menu_edit) {
            EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(mDataSource.getItemAt(mLastSelectedPosition));
            editDialogFragment.setListener(((name, description, date) -> {
                mDataSource.update(mLastSelectedPosition, new Note(name, description, date));
                mAdapter.notifyDataSetChanged();
            }));
            editDialogFragment.show(getActivity().getSupportFragmentManager(), getResources().getString(R.string.editFragment));
        } else if (item.getItemId() == R.id.note_list_item_menu_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.create().setTitle(getResources().getString(R.string.confirm_delete));
            builder.setMessage(getResources().getString(R.string.confirm_delete))
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        mDataSource.remove(mLastSelectedPosition);
                        mAdapter.notifyDataSetChanged();
                    }).setNegativeButton(R.string.no, (dialog, id) -> {
                dialog.dismiss();
            });
            builder.show();
        } else {
            return super.onContextItemSelected(item);
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setLastSelectedPosition(int lastSelectedPosition) {
        mLastSelectedPosition = lastSelectedPosition;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.notes_list_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_clear) {
            mDataSource.clear();
            mAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkLandScapeOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }
}