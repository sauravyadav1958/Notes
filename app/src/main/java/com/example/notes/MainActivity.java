package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Entity.Word;
import com.example.notes.RoomDatabase.WordRoomDatabase;
import com.example.notes.ViewModel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private WordListAdapter adapter;
    public static WordRoomDatabase INSTANCE;
    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private WordListAdapter wordListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        INSTANCE = WordRoomDatabase.getDatabase(this);

//        WordRoomDatabase room = Room.databaseBuilder(this, WordRoomDatabase.class, "Word")
//                .addCallback(sRoomDatabaseCallback)
//                .build();
//        LiveData<List<Word>> wordList = room.wordDao().getAlphabetizedWords();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // new WordListAdapter.WordDiff() will make the adapter efficient in listUpdating
        adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // better way of initializing viewModel.
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        mWordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
//            efficiently update only the modified parts of the list using WordDiff.
            // present in ListAdapter or PagedListAdapter
            adapter.submitList(words);
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
//              INTENT :  message that is passed between components
//              TYPES : implicit, explicit
//              IMPLICIT : doesn't specify the component
//              EXPLICIT:  specifies the component
//              getIntent() can be used in target class for getting intent.
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            // TODO replace the deprecated
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(Objects.requireNonNull(data.getStringExtra(NewWordActivity.EXTRA_REPLY)));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                WordDao dao = INSTANCE.wordDao();
//                dao.deleteAll();
//
//                Word word = new Word("Hello");
//                dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
//            });
//        }
//    };
}