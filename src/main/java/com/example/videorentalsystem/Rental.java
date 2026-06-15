package com.example.videorentalsystem;

public class Rental implements java.io.Serializable {
    private int id; // Holds the rentals ledger table Primary Key barcode
    private Customer customer; // Composition link: Maps to client_id Foreign Key in the DB
    private Movie movie;       // Composition link: Maps to movie_id Foreign Key in the DB
    private boolean isReturned; // Tracks state: false = Borrowed, true = Returned

    // Constructor 1: Used when creating a new rental transaction locally at the counter
    public Rental(Customer customer, Movie movie) {
        this.customer = customer;
        this.movie = movie;
        this.isReturned = false; // Defaults to false because it's just being borrowed
    }

    // Constructor 2: Used when loading an existing rental transaction history from the database vault
    public Rental(int id, Customer customer, Movie movie, boolean isReturned) {
        this.id = id;
        this.customer = customer;
        this.movie = movie;
        this.isReturned = isReturned;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void markAsReturned() {
        this.isReturned = true;
    }

    @Override
    public String toString() {
        // Ternary operator to print a clean receipt indicator prefix in your visual UI list
        String status = isReturned ? "[Returned]" : "[Borrowed]";
        return status + " " + customer.getFullName() + " rented " + movie.getTitle();
    }
}