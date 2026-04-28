package com.example.videorentalsystem;

public class Rental {
    private Customer customer;
    private Movie movie;
    private boolean isReturned;

    public Rental(Customer customer, Movie movie) {
        this.customer = customer;
        this.movie = movie;
        this.isReturned = false;
    }

    public Customer getCustomer() { return customer; }
    public Movie getMovie() { return movie; }
    public boolean isReturned() { return isReturned; }

    public void markAsReturned() {
        this.isReturned = true;
    }

    @Override
    public String toString() {
        String status = isReturned ? "[Returned]" : "[Borrowed]";
        return status + " " + customer.getFullName() + " rented " + movie.getTitle();
    }
}
