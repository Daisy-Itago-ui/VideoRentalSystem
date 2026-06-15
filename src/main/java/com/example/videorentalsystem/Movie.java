package com.example.videorentalsystem;

public class Movie implements java.io.Serializable {
    private int id; // Holds the database Primary Key barcode
    private String title;
    private Genre genre; // Composition link: physically connects a Movie to its Genre object

    // Constructor 1: Used when creating a new movie locally before saving to DB
    public Movie(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    // Constructor 2: Used when loading an existing movie out of the database vault
    public Movie(int id, String title, Genre genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return title; // Ensures your JavaFX lists display the movie title text cleanly on screen
    }
}