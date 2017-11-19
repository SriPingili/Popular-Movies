package com.example.android.popularmovies.model;

import java.io.Serializable;

/**
 *
 */


public class Trailer implements Serializable {

    private String videoKey;
    private String videoName;
    private String site;
    private String videoType;

    public Trailer(String videoKey, String videoName, String site, String videoType) {
        this.videoKey = videoKey;
        this.videoName = videoName;
        this.site = site;
        this.videoType = videoType;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getSite() {
        return site;
    }

    public String getVideoType() {
        return videoType;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
