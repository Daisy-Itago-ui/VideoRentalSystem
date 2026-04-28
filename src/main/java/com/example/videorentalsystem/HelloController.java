package com.example.videorentalsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    // --- 1. THE MASTER BUCKETS ---
    private ObservableList<Genre> allGenres = FXCollections.observableArrayList();
    private ObservableList<Movie> allMovies = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Rental> allRentals = FXCollections.observableArrayList();

    // --- 2. THE UI LINKS ---
    @FXML private TextField txtGenreName;
    @FXML private ListView<Genre> listGenres;

    @FXML private TextField txtMovieTitle;
    @FXML private ComboBox<Genre> comboMovieGenre;
    @FXML private ListView<Movie> listMovies;

    @FXML private TextField txtCustomerName;
    @FXML private ListView<Customer> listCustomers;

    @FXML private ComboBox<Customer> comboRentalCustomer;
    @FXML private ComboBox<Movie> comboRentalMovie;
    @FXML private ListView<Rental> listRentals;

    // --- 3. THE ACTIONS ---

    @FXML
    protected void onSaveGenreClick() {
        if (!txtGenreName.getText().isEmpty()) {
            allGenres.add(new Genre(txtGenreName.getText()));
            listGenres.setItems(allGenres);
            comboMovieGenre.setItems(allGenres);
            txtGenreName.clear();
        }
    }

    @FXML
    protected void onSaveMovieClick() {
        if (!txtMovieTitle.getText().isEmpty() && comboMovieGenre.getValue() != null) {
            allMovies.add(new Movie(txtMovieTitle.getText(), comboMovieGenre.getValue()));
            listMovies.setItems(allMovies);
            comboRentalMovie.setItems(allMovies); // Update the rental dropdown
            txtMovieTitle.clear();
        }
    }

    @FXML
    protected void onSaveCustomerClick() {
        if (!txtCustomerName.getText().isEmpty()) {
            allCustomers.add(new Customer(txtCustomerName.getText()));
            listCustomers.setItems(allCustomers);
            comboRentalCustomer.setItems(allCustomers); // Update the rental dropdown
            txtCustomerName.clear();
        }
    }

    @FXML
    protected void onRentMovieClick() {
        Customer c = comboRentalCustomer.getValue();
        Movie m = comboRentalMovie.getValue();
        if (c != null && m != null) {
            allRentals.add(new Rental(c, m));
            listRentals.setItems(allRentals);
        }
    }
}