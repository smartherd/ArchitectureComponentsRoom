package com.androidarchitecture.learn.noteapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }

    @PrimaryKey
    @NonNull
    private final String id;

    @NonNull
    @ColumnInfo(name = "note")
    private final String mNote;

    public Note(String id, String note) {
        this.id = id;
        this.mNote = note;
    }



}
