package com.example.android.popularmovies.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Custom Adapter for the Grid View.
 */

public class CustomMovieAdapter extends ArrayAdapter<Movie> {

    public CustomMovieAdapter(Activity conntext, List<Movie> movies) {
        super(conntext, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }
        final ImageView imageView = convertView.findViewById(R.id.thumbnail_id);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getMoviePosterUrl()).into(imageView);
        return convertView;
    }
}
