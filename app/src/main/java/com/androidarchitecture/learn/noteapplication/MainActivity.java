package com.androidarchitecture.learn.noteapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private final String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteListAdapter = new NoteListAdapter(this, this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        //noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes(notes);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to insert note
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the note
            Note note = new Note(
                    data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATED_NOTE));
            noteViewModel.update(note);

            Toast.makeText(
                    getApplicationContext(),
                    R.string.updated,
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        // Code for Delete operation
        noteViewModel.delete(myNote);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
