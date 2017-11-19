package com.example.android.popularmovies.util;

import android.content.Context;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavoriteMoviesContract;
import com.example.android.popularmovies.data.PopularMoviesContract;
import com.example.android.popularmovies.data.TopRatedMoviesContract;

/**
 * Created by sp051821 on 11/18/17.
 */

public class HelperUtil {

    public static final String TOP_RATED = "toprated";
    public static final String POPULAR_MOVIES = "popular";
    public static final String FAVORITE_MOVIES= "favorites";
    public static final String MOVIE_DB_URL_POPULAR =
            "http://api.themoviedb.org/3/movie/popular?api_key=";
    public static final String MOVIE_DB_URL_TOP_RATED =
            "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    public static final String PICASO_LOAD_URL="http://image.tmdb.org/t/p/w185/";
    public static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    public static final String TRAILER_URL_EXTENSION ="/videos?api_key=";
    public static final String REVIEW_URL_EXTENSION ="/reviews?api_key=";


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

//
//    private void queryForFavoriteMovies() {
//        final ArrayList<Movie> favoriteMovies = new ArrayList<>();
//        final FavoriteMoviesDbHelper favoriteMoviesDbHelper = new FavoriteMoviesDbHelper(this);
//        sqLiteDatabase = favoriteMoviesDbHelper.getReadableDatabase();
//        final Cursor cursor = sqLiteDatabase.query(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME, null, null, null, null, null, FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_MOVIE_TITLE);
//        if (cursor.moveToFirst()) {
//            do {
//                String movieTitle = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_MOVIE_TITLE));
//                String moviePosterUrl = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_POSTER_URL));
//                String movieOverView = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_OVERVIEW));
//                String movieReleaseDate = cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE));
//                int movieUserRating = cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_USER_RATING));
//                long movieId = cursor.getLong(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_MOVIE_ID));
//
//                Movie movie = new Movie(movieTitle, moviePosterUrl, movieOverView, movieReleaseDate, movieUserRating, movieId);
//                favoriteMovies.add(movie);
//            } while (cursor.moveToNext());
//        }
//        else {
//            showErrorMessage();
//        }
//        cursor.close();
//        startIntentToMovieDetails(favoriteMovies);
//    }
//
//    private void queryForOfflinePopularMovies() {
//        final ArrayList<Movie> popularMovies = new ArrayList<>();
//        final PopularMoviesDbHelper popularMoviesDbHelper = new PopularMoviesDbHelper(this);
//        sqLiteDatabase = popularMoviesDbHelper.getReadableDatabase();
//
//        final Cursor cursor = sqLiteDatabase.query(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME, null, null, null, null, null, PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_TITLE);
//        if (cursor.moveToFirst()) {
//            do {
//                String movieTitle = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_TITLE));
//                String moviePosterUrl = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_URL));
//                String movieOverView = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW));
//                String movieReleaseDate = cursor.getString(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE));
//                int movieUserRating = cursor.getInt(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_USER_RATING));
//                long movieId = cursor.getLong(cursor.getColumnIndex(PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID));
//
//                Movie movie = new Movie(movieTitle, moviePosterUrl, movieOverView, movieReleaseDate, movieUserRating, movieId);
//                popularMovies.add(movie);
//            } while (cursor.moveToNext());
//        } else {
//            showErrorMessage();
//            errorMessageDisplay.setText(getResources().getString(R.string.internet_error_message));
//        }
//        cursor.close();
//        startIntentToMovieDetails(popularMovies);
//    }
//
//    private void queryForOfflineTopRatedMovies() {
//        final ArrayList<Movie> topRatedMovies = new ArrayList<>();
//        final TopRatedMoviesDbHelper topRatedMoviesDbHelper = new TopRatedMoviesDbHelper(this);
//        sqLiteDatabase = topRatedMoviesDbHelper.getReadableDatabase();
//        final Cursor cursor = sqLiteDatabase.query(TopRatedMoviesContract.TopRatedMoviesEntry.TABLE_NAME, null, null, null, null, null, TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_TITLE);
//        if (cursor.moveToFirst()) {
//            do {
//                String movieTitle = cursor.getString(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_TITLE));
//                String moviePosterUrl = cursor.getString(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_POSTER_URL));
//                String movieOverView = cursor.getString(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW));
//                String movieReleaseDate = cursor.getString(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE));
//                int movieUserRating = cursor.getInt(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_USER_RATING));
//                long movieId = cursor.getLong(cursor.getColumnIndex(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID));
//
//                Movie movie = new Movie(movieTitle, moviePosterUrl, movieOverView, movieReleaseDate, movieUserRating, movieId);
//                topRatedMovies.add(movie);
//            } while (cursor.moveToNext());
//        } else {
//            showErrorMessage();
//            errorMessageDisplay.setText(getResources().getString(R.string.internet_error_message));
//        }
//        cursor.close();
//        startIntentToMovieDetails(topRatedMovies);
//    }

//    private void insertDataToTopRatedMoviesTable(ArrayList<Movie> movies) {
//        TopRatedMoviesDbHelper topRatedMoviesDbHelper = new TopRatedMoviesDbHelper(this);
//        sqLiteDatabase = topRatedMoviesDbHelper.getWritableDatabase();
//        for (Movie movie : movies) {
//            ContentValues cv = new ContentValues();
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID, movie.getMovieId());
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_TITLE, movie.getMovieTitle());
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_POSTER_URL, movie.getMoviePosterUrl());
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW, movie.getMovieOverView());
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE, movie.getMovieReleaseDate());
//            cv.put(TopRatedMoviesContract.TopRatedMoviesEntry.COLUMN_USER_RATING, movie.getMovieUserRating());
//            sqLiteDatabase.insert(TopRatedMoviesContract.TopRatedMoviesEntry.TABLE_NAME, null, cv);
//        }
//    }
//
//    private void insertDataToPopularMoviesTable(ArrayList<Movie> movies) {
//
//        PopularMoviesDbHelper popularMoviesDbHelper = new PopularMoviesDbHelper(this);
//        sqLiteDatabase = popularMoviesDbHelper.getWritableDatabase();
//
//        int count = 0;
//        for (Movie movie : movies) {
//            ContentValues cv = new ContentValues();
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID, movie.getMovieId());
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_MOVIE_TITLE, movie.getMovieTitle());
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_POSTER_URL, movie.getMoviePosterUrl());
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW, movie.getMovieOverView());
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE, movie.getMovieReleaseDate());
//            cv.put(PopularMoviesContract.PopularMoviesEntry.COLUMN_USER_RATING, movie.getMovieUserRating());
//
//            long result = sqLiteDatabase.insert(PopularMoviesContract.PopularMoviesEntry.TABLE_NAME, null, cv);
//
//            if (result == movie.getMovieId()) {
//                count++;
//            }
//        }
//        if (movies.size() == count) {
//            Toast.makeText(this, "inserted succesfully", Toast.LENGTH_LONG).show();
//        }
//    }

}
