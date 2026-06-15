package com.example.videorentalsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    // --- 1. THE MASTER BUCKETS (Observable lists that update the screen dynamically) ---
    private ObservableList<Genre> allGenres = FXCollections.observableArrayList();
    private ObservableList<Movie> allMovies = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Rental> allRentals = FXCollections.observableArrayList();

    // --- 2. THE UI LINKS (Mapped directly to your fx:id tags in the .fxml file) ---
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

    // --- AUTOMATIC DATABASE INITIAL LOAD ---
    @FXML
    public void initialize() {
        // This executes the millisecond the window opens
        try {
            refreshAllUILists();
        } catch (Throwable t) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("FOUND THE EXACT RUNTIME CRASH:");
            t.printStackTrace();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        }
    }

    // Helper method to sync our JavaFX ObservableLists with the database tables
    private void refreshAllUILists() {
        // Clear local memory buckets to prevent duplicate stacking when items are refreshed
        allGenres.clear();
        allMovies.clear();
        allCustomers.clear();
        allRentals.clear();

        // Populate local memory lists with fresh data from MariaDB DAOs
        allGenres.addAll(GenreDAO.getAllGenres());
        allMovies.addAll(MovieDAO.getAllMovies());
        allCustomers.addAll(CustomerDAO.getAllCustomers());

        // OPTIONAL: If your assignment requires showing historical transactions from database on start
        allRentals.addAll(RentalDAO.getAllRentals());

        // Push the synchronized data directly into your visual UI components
        listGenres.setItems(allGenres);
        comboMovieGenre.setItems(allGenres);

        listMovies.setItems(allMovies);
        comboRentalMovie.setItems(allMovies);

        listCustomers.setItems(allCustomers);
        comboRentalCustomer.setItems(allCustomers);

        listRentals.setItems(allRentals);
    }

    // --- 3. THE INTERACTIVE BUTTON ACTIONS ---

    @FXML
    protected void onSaveGenreClick() {
        String name = txtGenreName.getText().trim();
        if (!name.isEmpty()) {
            // 1. Create the local object
            Genre newGenre = new Genre(name);
            // 2. Save it permanently to MariaDB via the DAO layer
            GenreDAO.saveGenre(newGenre);

            // 3. Clear text box input and pull fresh synchronized data from the database
            txtGenreName.clear();
            refreshAllUILists();
        }
    }

    @FXML
    protected void onSaveMovieClick() {
        String title = txtMovieTitle.getText().trim();
        Genre selectedGenre = comboMovieGenre.getValue();

        if (!title.isEmpty() && selectedGenre != null) {
            // 1. Create the local object (OOP Composition link)
            Movie newMovie = new Movie(title, selectedGenre);
            // 2. Save it permanently to MariaDB (Maps properties to foreign key IDs)
            MovieDAO.saveMovie(newMovie);

            // 3. Clear text input and refresh lists
            txtMovieTitle.clear();
            refreshAllUILists();
        }
    }

    @FXML
    protected void onSaveCustomerClick() {
        String fullName = txtCustomerName.getText().trim();
        if (!fullName.isEmpty()) {
            // 1. Create the local object
            Customer newCustomer = new Customer(fullName);
            // 2. Save it permanently to MariaDB clients table
            CustomerDAO.saveCustomer(newCustomer);

            // 3. Clear text input box and sync UI lists
            txtCustomerName.clear();
            refreshAllUILists();
        }
    }

    // 🔄 MODIFIED SECTION: Handles database mapping AND real-time display simultaneously
    @FXML
    protected void onRentMovieClick() {
        Customer c = comboRentalCustomer.getValue();
        Movie m = comboRentalMovie.getValue();

        if (c != null && m != null) {
            // 1. Create the local transaction object
            Rental newRental = new Rental(c, m);

            // 2. Save it permanently to MariaDB rentals ledger table
            RentalDAO.saveRental(newRental);

            // 3. Force the visual interface list to receive the local transaction tracking details
            allRentals.add(newRental);
            listRentals.setItems(allRentals);

            // 4. Reset dropdown menu lists cleanly back to unselected defaults
            comboRentalCustomer.setValue(null);
            comboRentalMovie.setValue(null);

            // 5. Run general housekeeping list refreshes smoothly
            refreshAllUILists();
        }
    }
}
