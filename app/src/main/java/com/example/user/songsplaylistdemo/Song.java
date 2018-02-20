package com.example.user.songsplaylistdemo;

/**
 * Created by USER on 2/18/2018.
 */

public class Song {

    public static final String colorRed = "red";
    public static final String colorGreen = "green";
    public static final String colorDefault = "default";
    public static final String colorYellow = "yellow";
    private int id;
    private String songName;
    private String artistName;
    private String lyrics;
    private String color;
    private boolean isExpanded;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Song()
    {
        this.color = colorDefault;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Song))
            return false;

        Song song = (Song) obj;
        return (this.getId() == song.getId());
    }
}
