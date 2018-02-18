package com.example.user.songsplaylistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/18/2018.
 */


public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    Context context;
    List<Song> songs;
    LayoutInflater layoutInflater;

    public SongsAdapter(Context context, ArrayList<Song> songs) {
        super();
        this.context = context;
        this.songs = songs;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.song_row, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, final int position) {

        Song item = this.songs.get(position);
        holder.songNameTextView.setText(item.getSongName());
        holder.artistNameTextView.setText(item.getArtistName());
        holder.lyricsTextView.setText(item.getLyrics());
    }

    @Override
    public int getItemCount() {
        return this.songs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout songRelativeLayout;
        TextView songNameTextView, artistNameTextView,lyricsTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.songRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.songRelativeLayout);
            this.songNameTextView = (TextView) itemView.findViewById(R.id.songNameTextView);
            this.artistNameTextView = (TextView) itemView.findViewById(R.id.artistNameTextView);
            this.lyricsTextView = (TextView) itemView.findViewById(R.id.lyricsTextView);

            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lyricsTextView.getVisibility() == View.VISIBLE)
                        lyricsTextView.setVisibility(View.GONE);
                    else
                        lyricsTextView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}
