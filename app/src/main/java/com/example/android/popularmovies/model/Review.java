package com.example.android.popularmovies.model;

import java.io.Serializable;

/**
 * Created by sp051821 on 11/11/17.
 */

public class Review implements Serializable {

    private String author;
    private String reviewUrl;

    public Review(String author, String reviewUrl) {
        this.author = author;
        this.reviewUrl = reviewUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}
