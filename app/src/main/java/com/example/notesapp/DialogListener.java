package com.example.notesapp;

import java.io.Serializable;

public interface DialogListener extends Serializable {
    void getData(String name, String description, String date);
}
