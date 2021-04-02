package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Note> notes;
    private final NoteListFragment mFragment;

    public interface OnNoteClickListener {
        void onNoteClick(Note note, int position);
    }

    private final OnNoteClickListener onClickListener;

    public NoteAdapter(NoteListFragment context, List<Note> notes, OnNoteClickListener onClickListener) {
        this.notes = notes;
        this.mFragment = context;
        this.inflater = context.getLayoutInflater();
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.note, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.populate(mFragment, note);
        holder.itemView.setOnClickListener((v) -> onClickListener.onNoteClick(note, position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.clear(mFragment);
    }
}
