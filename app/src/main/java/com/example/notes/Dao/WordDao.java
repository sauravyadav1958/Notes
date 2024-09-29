package com.example.notes.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notes.Entity.Word;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE from Word")
    void deleteAll();

    @Query("SELECT * FROM Word ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
