package com.example.victor_tarus.magicnotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    public int getId() {
        return id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String datetime;
    private String content;

    public Note(String title, String datetime, String content) {
        this.title = title;
        this.datetime = datetime;
        this.content = content;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    public String getDatetime() {
        return datetime;
    }

}
