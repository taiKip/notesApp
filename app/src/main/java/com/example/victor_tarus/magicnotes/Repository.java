package com.example.victor_tarus.magicnotes;

import android.app.Application;
import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private  NoteDao noteDao;
    private LiveData<List<Note>> notesList;


    public Repository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        notesList =noteDao.getNotes();
    }
    public void insert(Note note){
new InsertNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
new deleteNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
new  deleteAllNotesAsyncTask(noteDao).execute();
    }
    public  LiveData<List<Note>> getNotesList(){
return  notesList;
    }
    private  static  class  InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private  static  class  UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private  static  class  deleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public deleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private  static  class  deleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        public deleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
