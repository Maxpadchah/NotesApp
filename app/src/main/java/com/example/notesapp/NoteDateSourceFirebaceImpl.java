package com.example.notesapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteDateSourceFirebaceImpl extends BaseNoteDataSource {
    private final static String TAG = "NoteDataImpl";

    private final static String COLLECTION_NOTES = "CollectionNotes";
    private volatile static NoteDateSourceFirebaceImpl sInstance;
    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mCollection = mStore.collection(COLLECTION_NOTES);


    public static NoteDateSourceFirebaceImpl getInstance() {
        NoteDateSourceFirebaceImpl instance = sInstance;
        if (instance == null) {
            synchronized (NoteDataSourceImpl.class) {
                if (sInstance == null) {
                    instance = new NoteDateSourceFirebaceImpl();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }


    private NoteDateSourceFirebaceImpl() {
        mCollection.orderBy("date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(this::onFetchComplete)
                .addOnFailureListener(this::onFetchFailed);

    }

    private void onFetchComplete(Task<QuerySnapshot> task) {

        if (task.isSuccessful()) {
            List<Note> data = new ArrayList<>();
            mData.clear();
            mData.addAll(data);
            data.clear();
            notifyDataSetChanged();
        }
    }

    private void onFetchFailed(Exception e) {
        Log.d(TAG, "Fetch failed", e);
    }


    @Override
    public List<Note> getNotes() {
        return Collections.unmodifiableList(mData);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }

    @Override
    public Note getItemAt(int idx) {
        return mData.get(idx);
    }


    @Override
    public void add(@NonNull Note note) {
        final NoteDataFromFirestore noteData;
        if (note instanceof NoteDataFromFirestore) {
            noteData = (NoteDataFromFirestore) note;
        } else {
            noteData = new NoteDataFromFirestore(note);
        }
        mData.add(noteData);
        mCollection.add(noteData.getFields())
                .addOnSuccessListener(documentReference -> {
                    noteData.setId(documentReference.getId());
                });
    }

    @Override
    public void update(int position, Note note) {
        String id = mData.get(position).getId();
        DocumentReference documentReference = mStore.collection(COLLECTION_NOTES).document(id);
        documentReference.update("date", note.getDate(),
                "description", note.getDescription(),
                "name", note.getName()
        );
        mData.set(position, note);

    }

    @Override
    public void remove(int position) {
        String id = mData.get(position).getId();
        mCollection.document(id).delete();
        super.remove(position);
    }

    @Override
    public void clear() {
        for (Note note : mData) {
            mCollection.document(note.getId()).delete();
        }
        mData.clear();
    }
}
