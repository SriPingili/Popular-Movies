package com.example.android.popularmovies.model;

import java.io.Serializable;

/**
 * Model class that represents the Movie Object. This
 * stores the title, thumbnail url, movie synopsis,
 * release data and th average user rating.
 */

public class Movie implements Serializable {

    private String movieTitle;
    private String moviePosterUrl;
    private String movieOverView;
    private String movieReleaseDate;
    private int movieUserRating;
    private long movieId;

    public Movie(String movieTitle, String moviePosterUrl, String movieOverView, String movieReleaseDate, int movieUserRating, long movieId) {
        this.movieTitle = movieTitle;
        this.moviePosterUrl = moviePosterUrl;
        this.movieOverView = movieOverView;
        this.movieReleaseDate = movieReleaseDate;
        this.movieUserRating = movieUserRating;
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public String getMovieOverView() {
        return movieOverView;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public int getMovieUserRating() {
        return movieUserRating;
    }

    public long getMovieId() {
        return movieId;
    }

}
