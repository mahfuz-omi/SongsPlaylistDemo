package com.example.user.songsplaylistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/18/2018.
 */


public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> implements Filterable{

    Context context;
    List<Song> allSongs;
    List<Song> suggestedSongs;
    LayoutInflater layoutInflater;

    public SongsAdapter(Context context, ArrayList<Song> songs) {
        super();
        this.context = context;
        this.allSongs = songs;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.suggestedSongs = this.allSongs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.song_row, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, final int position) {

        Song item = this.suggestedSongs.get(position);
        holder.songNameTextView.setText(item.getSongName());
        holder.artistNameTextView.setText(item.getArtistName());
        holder.lyricsTextView.setText(item.getLyrics());
        holder.songId.setText(item.getId()+"");
    }

    @Override
    public int getItemCount() {
        return this.suggestedSongs.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        View view;
        RelativeLayout songRelativeLayout;
        TextView songNameTextView, artistNameTextView,lyricsTextView, songId;

        public SongViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.songRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.songRelativeLayout);
            this.songNameTextView = (TextView) itemView.findViewById(R.id.songNameTextView);
            this.artistNameTextView = (TextView) itemView.findViewById(R.id.artistNameTextView);
            this.lyricsTextView = (TextView) itemView.findViewById(R.id.lyricsTextView);
            this.songId = (TextView) itemView.findViewById(R.id.songId);

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


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                suggestedSongs = findSongs(context, constraint.toString());

                // Assign the data to the FilterResults
                filterResults.values = suggestedSongs;
                filterResults.count = suggestedSongs.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            suggestedSongs = (List<Song>) results.values;
            notifyDataSetChanged();

        }
    };

    private List<Song> findSongs(Context context, String songOrArtistname)
    {
        songOrArtistname = songOrArtistname.trim();
        List<Song> songs = new ArrayList<>();

        for(Song song:this.allSongs)
        {
            String songName = song.getSongName().trim();
            String artistName = song.getArtistName().trim();
            //String lyrics = song.getLyrics().trim();
            if(songName.indexOf(songOrArtistname) != -1 || artistName.startsWith(songOrArtistname) )
            {
                songs.add(song);
            }
        }
        return songs;
    }

}
