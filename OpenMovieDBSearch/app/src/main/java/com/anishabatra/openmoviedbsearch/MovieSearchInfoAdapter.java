package com.anishabatra.openmoviedbsearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MovieSearchInfoAdapter extends RecyclerView.Adapter<MovieSearchInfoViewHolder> {
    private ArrayList<MovieSearchInfo> movieSearchInfos;

    public MovieSearchInfoAdapter(ArrayList<MovieSearchInfo> movieSearchInfos) {
        this.movieSearchInfos = movieSearchInfos;
    }

    @NonNull
    @Override
    public MovieSearchInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_movie_search_item_list, viewGroup, false);

        MovieSearchInfoViewHolder movieSearchInfoViewHolder = new MovieSearchInfoViewHolder(v);

        return  movieSearchInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchInfoViewHolder movieSearchInfoViewHolder, int i) {
        movieSearchInfoViewHolder.setMovieInfoItem(movieSearchInfos.get(i));
    }

    @Override
    public int getItemCount() {
        return movieSearchInfos.size();
    }
}