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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        this.suggestedSongs = new ArrayList<>(this.allSongs);
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
        LinearLayout songLinearLayout;
        LinearLayout songExpandableLinearLayout, rootLinearLayout;
        TextView songNameTextView, artistNameTextView,lyricsTextView, songId;

        Button buttonGreen, buttonRed, buttonDefault, buttonYellow, buttonPaste;
        ImageView addToFavouriteImageView;

        public SongViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.songLinearLayout = (LinearLayout) itemView.findViewById(R.id.songLinearLayout);
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
            this.buttonPaste = (Button) itemView.findViewById(R.id.buttonPaste);
            this.addToFavouriteImageView = (ImageView) itemView.findViewById(R.id.addToFavouriteImageView);

            this.addToFavouriteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Song song = suggestedSongs.get(getAdapterPosition());

                    // delete from DB
                    removeSongById(allSongs,song.getId(),"songs");
                    //Paper.book().write("songs",allSongs);


                    // save as favourite songs in DB
                    List<Song> favouriteSongs = Paper.book().read("favourite_songs",new ArrayList<Song>());
                    favouriteSongs.add(song);
                    Paper.book().write("favourite_songs",favouriteSongs);

                    // delete this song view
                    suggestedSongs.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    Toast.makeText(context, "Song added to Favourite", Toast.LENGTH_SHORT).show();


                }
            });



            this.buttonYellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Song song = suggestedSongs.get(getAdapterPosition());

                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                    song.setColor(Song.colorYellow);

                    // save into DB
                    changeColorById(allSongs,song.getId(),Song.colorYellow,"songs");
                }
            });

            this.buttonRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = suggestedSongs.get(getAdapterPosition());

                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
                    song.setColor(Song.colorRed);

                    // save into DB
                    changeColorById(allSongs,song.getId(),Song.colorRed,"songs");


                }
            });

            this.buttonGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = suggestedSongs.get(getAdapterPosition());

                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                    song.setColor(Song.colorGreen);

                    // save into DB
                    changeColorById(allSongs,song.getId(),Song.colorGreen,"songs");


                }
            });

            this.buttonDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = suggestedSongs.get(getAdapterPosition());

                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    song.setColor(Song.colorDefault);

                    // save into DB
                    changeColorById(allSongs,song.getId(),Song.colorDefault,"songs");


                }
            });


            this.buttonPaste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song song = suggestedSongs.get(getAdapterPosition());

                    // color yellow
                    rootLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPaste));
                    song.setColor(Song.colorPaste);

                    // save into DB
                    changeColorById(allSongs,song.getId(),Song.colorPaste,"songs");


                }
            });


            this.songLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(songExpandableLinearLayout.getVisibility() == View.VISIBLE)
                    {
                        Song song = suggestedSongs.get(getAdapterPosition());
                        songExpandableLinearLayout.setVisibility(View.GONE);
                        // save to DB
                        changeExpansionById(allSongs,song.getId(),false,"songs");
                    }

                    else
                    {
                        Song song = suggestedSongs.get(getAdapterPosition());
                        songExpandableLinearLayout.setVisibility(View.VISIBLE);
                        // save to DB
                        changeExpansionById(allSongs,song.getId(),true,"songs");
                    }

                }
            });

            this.lyricsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(songExpandableLinearLayout.getVisibility() == View.VISIBLE)
                    {
                        Song song = suggestedSongs.get(getAdapterPosition());
                        songExpandableLinearLayout.setVisibility(View.GONE);
                        // save to DB
                        changeExpansionById(allSongs,song.getId(),false,"songs");
                    }

                    else
                    {
                        Song song = suggestedSongs.get(getAdapterPosition());
                        songExpandableLinearLayout.setVisibility(View.VISIBLE);
                        // save to DB
                        changeExpansionById(allSongs,song.getId(),true,"songs");
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

    public static void removeSongById(List<Song> songs,int id,String key)
    {
        Song itemToBeRemoved = null;
        for(Song song:songs)
        {
            if(song.getId() == id)
                itemToBeRemoved = song;
        }
        songs.remove(itemToBeRemoved);
        Paper.book().write(key,songs);
    }

    public static void changeColorById(List<Song> songs,int id,String color,String key)
    {
        for(Song song:songs)
        {
            if(song.getId() == id)
            {
                song.setColor(color);
            }
        }
        Paper.book().write(key,songs);
    }

    public static void changeExpansionById(List<Song> songs,int id,boolean isExpanded,String key)
    {
        for(Song song:songs)
        {
            if(song.getId() == id)
            {
                song.setExpanded(isExpanded);
            }
        }
        Paper.book().write(key,songs);
    }

}
