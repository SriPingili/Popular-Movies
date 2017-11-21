package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for the Popular movies table
 */
public class PopularMoviesContract {
    public static final String PATH_POPULAR_MOVIES = "popular_movies";
    public static final String AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class PopularMoviesEntry implements BaseColumns {
        public static final Uri POPULAR_MOVIES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();
        public static final String TABLE_NAME = "popular_movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_POSTER_URL = "movie_poster_url";
        public static final String COLUMN_OVERVIEW = "movie_overview";
        public static final String COLUMN_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_USER_RATING = "user_rating";
    }
}