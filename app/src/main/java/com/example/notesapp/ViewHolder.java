package com.example.notesapp;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    final TextView tvName;
    final TextView tvDescription;
    final TextView tvDate;

    ViewHolder(View view) {
        super(view);
        tvName = view.findViewById(R.id.tvName);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvDate = view.findViewById(R.id.tvDate);
    }

    public void populate(NoteListFragment fragment, Note note) {
        tvName.setText(note.getName());
        tvDescription.setText(note.getDescription());
        tvDate.setText(note.getDate());
        itemView.setOnLongClickListener((v) -> {
            fragment.setLastSelectedPosition(getLayoutPosition());
            return false;
        });

        fragment.registerForContextMenu(itemView);
    }

    public void clear(Fragment fragment) {
        itemView.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(itemView);
    }
}
