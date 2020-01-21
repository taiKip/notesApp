package com.example.victor_tarus.magicnotes;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContentSave extends AppCompatActivity {
private EditText ed_title;
private EditText ed_content;
public static final String EXTRA_TITLE = "com.example.victor_tarus.magicnotes.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.victor_tarus.magicnotes.EXTRA_CONTENT";
    public static final String EXTRA_DATE ="com.example.victor_tarus.magicnotes.EXTRA_DATE";
    public static final String EXTRA_NOTEID= "com.example.victor_tarus.magicnotes.EXTRA_NOTEID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_save);
        Toolbar toolbar = findViewById(R.id.toolbar_save);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_saveNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
save();
            }
        });
        ed_title =findViewById(R.id.ed_title);
        ed_content = findViewById(R.id.ed_content);
        Intent i = getIntent();
        if (i.hasExtra(EXTRA_NOTEID)){
            ed_title.setText(i.getStringExtra(EXTRA_TITLE));
            ed_content.setText(i.getStringExtra(EXTRA_CONTENT));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cancel:
                save();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
public  void save(){
    String date = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String title = ed_title.getText().toString();
        String content = ed_content.getText().toString();
        if ((title.trim().isEmpty())||content.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.cannotSave,Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Intent z = new Intent();
            z.putExtra(EXTRA_TITLE,title);
            z.putExtra(EXTRA_CONTENT,content);
            z.putExtra(EXTRA_DATE,date);
            setResult(RESULT_OK,z);
        int id  = getIntent().getIntExtra(EXTRA_NOTEID,-1);
        if(id != -1){
            z.putExtra(EXTRA_NOTEID,id);
        }
            finish();
        }

}
}
