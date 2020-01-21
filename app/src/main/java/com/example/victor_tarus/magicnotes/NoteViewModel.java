package com.example.victor_tarus.magicnotes;

import android.app.Application;
import android.app.ListActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private  Repository repository ;
    private LiveData<List<Note>> allnotes;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allnotes = repository.getNotesList();
    }
    public  void insertnote(Note note){
        repository.insert(note);
    }
    public  void updatenote(Note note){
        repository.update(note);
    }
    public  void delete(Note note){
        repository.delete(note);
    }
    public  void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>>getAllnotes(){
        return allnotes;
    }
}
