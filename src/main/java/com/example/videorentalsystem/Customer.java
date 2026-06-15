package com.example.videorentalsystem;

public class Customer implements java.io.Serializable {
    private int id;
    private String fullName;

    // Constructor 1: Used when creating a new customer locally at the counter
    public Customer(String fullName) {
        this.fullName = fullName;
    }

    // Constructor 2: Used when fetching an existing client out of the database vault
    public Customer(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    // Restores your original method name precisely
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}