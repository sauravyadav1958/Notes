package com.example.notes.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    // autoGenerate if not present,
    // primaryKey will not be generated hence data will not saved in db.
    //TODO -> But primaryKey value if put manually might help??
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String word;
    // since word can't be null, it has to be initialized in the constructor
    public Word(@NonNull String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }

}
