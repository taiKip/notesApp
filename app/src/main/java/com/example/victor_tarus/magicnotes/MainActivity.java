package com.example.victor_tarus.magicnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static com.example.victor_tarus.magicnotes.ContentSave.EXTRA_CONTENT;
import static com.example.victor_tarus.magicnotes.ContentSave.EXTRA_DATE;
import static com.example.victor_tarus.magicnotes.ContentSave.EXTRA_NOTEID;
import static com.example.victor_tarus.magicnotes.ContentSave.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {
private  NoteViewModel noteViewModel;
public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODEUPDATE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
    @Override
    public void onChanged(List<Note> notes) {
      noteAdapter.setNotes(notes);
    }
});
        FloatingActionButton fab = findViewById(R.id.fab_addNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(MainActivity.this,ContentSave.class);
               startActivityForResult(i,REQUEST_CODE);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
noteViewModel.delete(noteAdapter.getNoteposition(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
noteAdapter.setOnItemClicklistener(new NoteAdapter.ItemClickedlistener() {
    @Override
    public void onItemClick(Note note) {
        Intent i = new Intent(MainActivity.this,ContentSave.class);
        i.putExtra(EXTRA_TITLE,note.getTitle());
        i.putExtra(EXTRA_CONTENT,note.getContent());
    i.putExtra(EXTRA_NOTEID,note.getId());
    startActivityForResult(i,REQUEST_CODEUPDATE);
    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_exit:
                finish();
                return true;
            case  R.id.action_delete:
                deleteAllmethod();//deletes all notes;
return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
        String title  = data.getStringExtra(EXTRA_TITLE);
        String datetime = data.getStringExtra(EXTRA_DATE);
        String content = data.getStringExtra(EXTRA_CONTENT);
        Note note = new Note(title, datetime, content);
        noteViewModel.insertnote(note);
    }else if (requestCode == REQUEST_CODEUPDATE && resultCode == RESULT_OK){
        int id = data.getIntExtra(ContentSave.EXTRA_NOTEID,-1);
        switch (id){
            case -1:
                Toast.makeText(this,R.string.notUpdated,Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        String title  = data.getStringExtra(EXTRA_TITLE);
        String datetime = data.getStringExtra(EXTRA_DATE);
        String content = data.getStringExtra(EXTRA_CONTENT);
        Note note = new Note(title, datetime, content);
        note.setId(id);
        noteViewModel.updatenote(note);
    }
    else {
        Toast.makeText(this,R.string.notSaved,Toast.LENGTH_SHORT).show();
    }
    }
    public  void deleteAllmethod(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.dialog).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteViewModel.deleteAllNotes();
            }
        }).setNegativeButton(R.string.cancel,null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
