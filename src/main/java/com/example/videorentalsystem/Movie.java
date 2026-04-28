package com.example.videorentalsystem;

public class Movie {
    private String title;
    private Genre genre;

    public Movie(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() { return title; }
    public Genre getGenre() { return genre; }

    @Override
    public String toString() {
        return title;
    }
}
