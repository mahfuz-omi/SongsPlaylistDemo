package com.example.user.songsplaylistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.paperdb.Paper;

public class FavouriteSongsActivity extends BaseActivity {
    ArrayList<Song> favouriteSongs;

    RecyclerView recyclerView;
    EditText searchSongEditText;
    FavouriteSongsAdapter favouriteSongsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_songs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initializeSongsFromDB();

        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        this.favouriteSongsAdapter = new FavouriteSongsAdapter(this,favouriteSongs);
        recyclerView.setAdapter(favouriteSongsAdapter);
        this.recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        this.recyclerView.setLayoutManager(linearLayoutManager);

        this.searchSongEditText = (EditText) this.findViewById(R.id.searchSongEditText);
        this.searchSongEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                favouriteSongsAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void initializeSongsFromDB()
    {
        this.favouriteSongs = Paper.book().read("favourite_songs",new ArrayList<Song>());
        Collections.sort(this.favouriteSongs);
    }
}
