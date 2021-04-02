package com.example.notesapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NoteDataFromFirestore extends Note {
    private static final  String FIELD_ID = "id";
    private static final  String FIELD_NAME = "name";
    private static final  String FIELD_DESCRIPTION = "description";
    private static final  String FIELD_DATE = "date";

    public NoteDataFromFirestore(String name, String description, String date) {
        super(name, description, date);
    }
    public NoteDataFromFirestore(String id, String name, String description, String date) {
        this(name, description, date);
        setId(id);
    }

    public  NoteDataFromFirestore(String id, Map<String, Object> fields){
        this(id, (String) fields.get(FIELD_NAME), (String) fields.get(FIELD_DESCRIPTION), (String) fields.get(FIELD_DATE));
    }
    public NoteDataFromFirestore(Note data){
        this(data.getId(), data.getName(), data.getDescription(), data.getDate());
    }


    public final Map<String, Object> getFields(){
        HashMap<String, Object> fields = new HashMap<>();
        fields.put(FIELD_NAME, getName());
        fields.put(FIELD_DESCRIPTION, getDescription());
        fields.put(FIELD_DATE, getDate());
        return Collections.unmodifiableMap(fields);
    }

}
