package com.example.android.popularmovies.util;

import android.content.Context;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavoriteMoviesContract;
import com.example.android.popularmovies.data.PopularMoviesContract;
import com.example.android.popularmovies.data.TopRatedMoviesContract;

/**
 * This is the utility class which contains the helper methods and constants
 */
public class HelperUtil {

    public static final String TOP_RATED = "toprated";
    public static final String POPULAR_MOVIES = "popular";
    public static final String FAVORITE_MOVIES = "favorites";
    public static final String MOVIE_DB_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String MOVIE_DB_URL_TOP_RATED =
            "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    public static final String PICASO_LOAD_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    public static final String TRAILER_URL_EXTENSION = "/videos?api_key=";
    public static final String REVIEW_URL_EXTENSION = "/reviews?api_key=";
    public static final String SCROLL_STATE_KEY = "scroll_state";


    public static String getApiKey(Context context) {
        return context.getString(R.string.api_key);
    }

    public static String[] getPopularMovieColumns() {
        final String[] popularMoviesColumns = {
                PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_TITLE,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_URL,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_USER_RATING,
                PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID
        };
        return popularMoviesColumns;
    }

    public static String[] getTopRatedMovieColumns() {
        final String[] topRatedMoviesColumns = {
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_TITLE,
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_POSTER_URL,
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW,
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE,
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_USER_RATING,
                TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID
        };
        return topRatedMoviesColumns;
    }

    public static String[] getFavoriteMovieColumns() {
        final String[] favoriteMovieColumns = {
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_MOVIE_TITLE,
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_POSTER_URL,
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_OVERVIEW,
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE,
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_USER_RATING,
                FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_MOVIE_ID
        };
        return favoriteMovieColumns;
    }
}