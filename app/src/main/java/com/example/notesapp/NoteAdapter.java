package com.example.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<com.example.notesapp.NoteAdapter.ViewHolder>{



    private final LayoutInflater inflater;
    private final List<Note> notes;
    public interface OnNoteClickListener{
        void onNoteClick(Note note, int position);
    }

    private final OnNoteClickListener onClickListener;

    public NoteAdapter(Context context, List<Note> notes, OnNoteClickListener onClickListener) {
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
        this.onClickListener = onClickListener;
    }
    @Override
    public com.example.notesapp.NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.notesapp.NoteAdapter.ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvName.setText(note.getName());
        holder.tvDescription.setText(note.getDescription());
        holder.tvDate.setText(note.getDate());
        holder.itemView.setOnClickListener((v) -> onClickListener.onNoteClick(note, position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvDescription;
        final TextView tvDate;
        ViewHolder(View view){
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvDescription =  view.findViewById(R.id.tvDescription);
            tvDate =  view.findViewById(R.id.tvDate);
        }
    }
}
