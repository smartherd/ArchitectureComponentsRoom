package com.androidarchitecture.learn.noteapplication;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private final String TAG = this.getClass().getSimpleName();
    private final NoteDao noteDao;
    private final NoteRoomDatabase db;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
    }

    public LiveData<Note> getNote(String noteId) {
        return noteDao.getNote(noteId);
    }
}
