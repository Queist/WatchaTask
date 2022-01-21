package com.example.watchatask.model;

/**
 *  곡을 추상화하는 class
 */
public class Music {
    private int id;
    private String track;
    private String collection;
    private String artist;
    private String artworkURL;

    private boolean isFavorite;

    public Music(int id, String track, String collection, String artist, String artWorkURL, boolean isFavorite) {
        this.id = id;
        this.track = track;
        this.collection = collection;
        this.artist = artist;
        this.artworkURL = artWorkURL;
        this.isFavorite = isFavorite;
    }

    public String getTrack() {
        return track;
    }

    public String getCollection() {
        return collection;
    }

    public String getArtist() {
        return artist;
    }

    public String getArtworkURL() {
        return artworkURL;
    }

    public void toggle() {
        this.isFavorite = !this.isFavorite;
    }

    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
