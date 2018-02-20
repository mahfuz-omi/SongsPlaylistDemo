package com.example.user.songsplaylistdemo;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

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

        //holder.setIsRecyclable(false);

        Song item = this.suggestedSongs.get(position);
        holder.songNameTextView.setText(item.getSongName());
        holder.artistNameTextView.setText(item.getArtistName());
        holder.lyricsTextView.setText(item.getLyrics());
        holder.songId.setText(item.getId()+"");
        holder.rootLinearLayout.setBackgroundColor(getColor(item.getColor()));
        if(suggestedSongs.get(position).isExpanded())
            holder.songExpandableLinearLayout.setVisibility(View.VISIBLE);
        else
            holder.songExpandableLinearLayout.setVisibility(View.GONE);
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
        LinearLayout songExpandableLinearLayout, rootLinearLayout;
        TextView songNameTextView, artistNameTextView,lyricsTextView, songId;

        Button buttonGreen, buttonRed, buttonDefault, buttonYellow;

        public SongViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.songRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.songRelativeLayout);
            this.songNameTextView = (TextView) itemView.findViewById(R.id.songNameTextView);
            this.artistNameTextView = (TextView) itemView.findViewById(R.id.artistNameTextView);
            this.lyricsTextView = (TextView) itemView.findViewById(R.id.lyricsTextView);
            this.songId = (TextView) itemView.findViewById(R.id.songId);
            this.songExpandableLinearLayout = (LinearLayout) itemView.findViewById(R.id.songExpandableLinearLayout);
            this.rootLinearLayout = (LinearLayout) itemView.findViewById(R.id.rootLinearLayout);

            this.buttonDefault = (Button) itemView.findViewById(R.id.buttonDefault);
            this.buttonRed = (Button) itemView.findViewById(R.id.buttonRed);
            this.buttonGreen = (Button) itemView.findViewById(R.id.buttonGreen);
            this.buttonYellow = (Button) itemView.findViewById(R.id.buttonYellow);



            this.buttonYellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                    suggestedSongs.get(getAdapterPosition()).setColor(Song.colorYellow);

                    // save into DB
                    allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setColor(Song.colorYellow);
                    Paper.book().write("songs",allSongs);


                }
            });

            this.buttonRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                    suggestedSongs.get(getAdapterPosition()).setColor(Song.colorRed);

                    // save into DB
                    allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setColor(Song.colorRed);
                    Paper.book().write("songs",allSongs);


                }
            });

            this.buttonGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                    suggestedSongs.get(getAdapterPosition()).setColor(Song.colorGreen);

                    // save into DB
                    allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setColor(Song.colorGreen);
                    Paper.book().write("songs",allSongs);


                }
            });

            this.buttonDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    suggestedSongs.get(getAdapterPosition()).setColor(Song.colorDefault);

                    // save into DB
                    allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setColor(Song.colorDefault);
                    Paper.book().write("songs",allSongs);


                }
            });

            this.songRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(songExpandableLinearLayout.getVisibility() == View.VISIBLE)
                    {
                        songExpandableLinearLayout.setVisibility(View.GONE);
                        // save to DB
                        allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setExpanded(false);
                        Paper.book().write("songs",allSongs);
                    }

                    else
                    {
                        songExpandableLinearLayout.setVisibility(View.VISIBLE);
                        // save to DB
                        allSongs.get(suggestedSongs.get(getAdapterPosition()).getId()-1).setExpanded(true);
                        Paper.book().write("songs",allSongs);
                    }

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

    public int getColor(String color)
    {
        Resources resources = context.getResources();
        if(Song.colorDefault.equalsIgnoreCase(color))
            return resources.getColor(R.color.colorPrimary);
        else if(Song.colorRed.equalsIgnoreCase(color))
            return resources.getColor(R.color.colorRed);
        else if(Song.colorYellow.equalsIgnoreCase(color))
            return resources.getColor(R.color.colorYellow);
        else if(Song.colorGreen.equalsIgnoreCase(color))
            return resources.getColor(R.color.colorGreen);

        return resources.getColor(R.color.colorPrimary);
    }


}
