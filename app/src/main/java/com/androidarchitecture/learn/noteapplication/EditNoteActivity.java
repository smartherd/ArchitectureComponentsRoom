package com.androidarchitecture.learn.noteapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    static final String UPDATED_NOTE = "note_text";
    private EditText etNote;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> note;

    EditNoteViewModel noteModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNote = findViewById(R.id.etNote);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }

        //noteModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        noteModel = new ViewModelProvider(this).get(EditNoteViewModel.class);

        note = noteModel.getNote(noteId);

        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                etNote.setText(note.getNote());

            }
        });        /*
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable @Nullable Note note) {
                etNote.setText(note.getNote());

            }
        });*/
    }

    public void updateNote(View view) {
        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate(View view) {
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
