package com.example.videorentalsystem;

public class Genre implements java.io.Serializable {
    private int id;
    private String name;

    // Constructor 1: Used when creating a new genre locally before saving to DB
    public Genre(String name) {
        this.name = name;
    }

    // Constructor 2: Used when loading an existing genre out of the database
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    // This restores the method that line 21 in GenreDAO is looking for!
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}