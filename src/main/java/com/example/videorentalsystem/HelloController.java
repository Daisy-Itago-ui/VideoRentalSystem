package com.example.videorentalsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * <h1>HelloController Class</h1>
 * This JavaFX Controller manages the User Interface on the Client side,
 * executing operations by sending requests to a centralized RMI Server application
 * instead of invoking direct database DAO handlers locally.
 *
 * @author Daisy Itago
 * @version 1.0
 */
public class HelloController {

    // --- 1. NETWORK CONTROLLER STUB LINK ---
    private RemoteRentalService remoteService;

    // --- 2. THE MASTER BUCKETS (Observable lists that update the screen dynamically) ---
    private ObservableList<Genre> allGenres = FXCollections.observableArrayList();
    private ObservableList<Movie> allMovies = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Rental> allRentals = FXCollections.observableArrayList();

    // --- 3. THE UI LINKS (Mapped directly to your fx:id tags in the .fxml file) ---
    @FXML private TextField txtGenreName;
    @FXML private ListView<Genre> listGenres;

    @FXML private TextField txtMovieTitle;
    @FXML private ComboBox<Genre> comboMovieGenre;
    @FXML private ComboBox<Movie> comboRegisteredMovies;
    @FXML private ComboBox<Customer> comboRegisteredCustomers;
    @FXML private ListView<Movie> listMovies;

    @FXML private TextField txtCustomerName;
    @FXML private ListView<Customer> listCustomers;

    @FXML private ComboBox<Customer> comboRentalCustomer;
    @FXML private ComboBox<Movie> comboRentalMovie;
    @FXML private ListView<Rental> listRentals;
    @FXML private ComboBox<Genre> comboRentalGenreFilter;
    @FXML private ComboBox<Rental> comboRentalBorrowed;
    @FXML private ComboBox<Rental> comboRentalReturned;

    // --- AUTOMATIC NETWORK INITIALIZATION LOAD ---
    @FXML
    public void initialize() {
        try {
            System.out.println("Client UI connecting to centralized RMI Server Registry...");

            // Step A: Locate the local loopback network registry running on port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Step B: Look up the remote proxy stub via its verified identifier name
            remoteService = (RemoteRentalService) registry.lookup("VlsRentalService");
            System.out.println("Network link established! Remote service stub successfully assigned.");

            // Trigger the initial data population across the network link
            refreshAllUILists();

        } catch (Throwable t) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("DISTRIBUTED NETWORK ATTACHMENT ERROR:");
            t.printStackTrace();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        }
    }

    // Helper method to sync our JavaFX ObservableLists with the database via the remote server stub
    private void refreshAllUILists() {
        try {
            // Clear local client memory window buckets to prevent duplicate stacking
            allGenres.clear();
            allMovies.clear();
            allCustomers.clear();
            allRentals.clear();

            // Populate local memory lists with fresh data retrieved via RMI network streams
            allGenres.addAll(remoteService.getAllGenres());
            allMovies.addAll(remoteService.getAllMovies());
            allCustomers.addAll(remoteService.getAllCustomers());
            allRentals.addAll(remoteService.getAllRentals());

            // Push the synchronized data directly into your visual UI components
            listGenres.setItems(allGenres);
            comboMovieGenre.setItems(allGenres);

            // CHANGED THIS LINE: Now points to your new dropdown menu ID
            comboRegisteredMovies.setItems(allMovies);
            comboRentalMovie.setItems(allMovies);

            comboRegisteredCustomers.setItems(allCustomers);
            comboRentalCustomer.setItems(allCustomers);

            // Map fresh genre data to the new left column filter menu
            comboRentalGenreFilter.setItems(allGenres);

// Populate the active tracking lists
            comboRentalBorrowed.setItems(allRentals);
            comboRentalReturned.setItems(allRentals);

        } catch (Exception e) {
            System.err.println("Network exception caught while refreshing layout lists:");
            e.printStackTrace();
        }
    }
    // --- 4. THE INTERACTIVE BUTTON ACTIONS ROUTED VIA RMI ---

    @FXML
    protected void onSaveGenreClick() {
        String name = txtGenreName.getText().trim();
        if (!name.isEmpty()) {
            try {
                // 1. Create the local data boundary object
                Genre newGenre = new Genre(name);

                // 2. Forward the object across the network stream to the Server
                remoteService.saveGenre(newGenre);

                // 3. Clean up the UI view interface
                txtGenreName.clear();
                refreshAllUILists();
            } catch (Exception e) {
                System.err.println("RMI transmission error on saving genre:");
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onSaveMovieClick() {
        String title = txtMovieTitle.getText().trim();
        Genre selectedGenre = comboMovieGenre.getValue();

        if (!title.isEmpty() && selectedGenre != null) {
            try {
                // 1. Create the local object layout structure
                Movie newMovie = new Movie(title, selectedGenre);

                // 2. Forward the object across the network stream to the Server
                remoteService.saveMovie(newMovie);

                // 3. Reset input states cleanly
                txtMovieTitle.clear();
                refreshAllUILists();
            } catch (Exception e) {
                System.err.println("RMI transmission error on saving movie:");
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onSaveCustomerClick() {
        String fullName = txtCustomerName.getText().trim();
        if (!fullName.isEmpty()) {
            try {
                // 1. Create the local identity record object
                Customer newCustomer = new Customer(fullName);

                // 2. Forward the object across the network stream to the Server
                remoteService.saveCustomer(newCustomer);

                // 3. Refresh interface components completely
                txtCustomerName.clear();
                refreshAllUILists();
            } catch (Exception e) {
                System.err.println("RMI transmission error on saving customer:");
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onRentMovieClick() {
        Customer c = comboRentalCustomer.getValue();
        Movie m = comboRentalMovie.getValue();

        if (c != null && m != null) {
            try {
                // 1. Create the local transaction assignment record object
                Rental newRental = new Rental(c, m);

                // 2. Log the transaction across the network stream into the server database
                remoteService.saveRental(newRental);

                // 3. Clear selections to refresh input slots
                comboRentalCustomer.setValue(null);
                comboRentalMovie.setValue(null);

                // 4. Force synchronization sequence
                refreshAllUILists();
            } catch (Exception e) {
                System.err.println("RMI transmission error on leasing transaction processing:");
                e.printStackTrace();
            }
        }
    }
}
