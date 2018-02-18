package com.example.user.songsplaylistdemo;

import android.app.Application;

import io.paperdb.Paper;

/**
 * Created by USER on 2/18/2018.
 */

public class SongApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
