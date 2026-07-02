package com.example.videorentalsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

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

    // --- 2. THE MASTER BUCKETS (Synchronized as clean Strings for immediate UI rendering) ---
    private ObservableList<String> allGenres = FXCollections.observableArrayList();
    private ObservableList<String> allMovies = FXCollections.observableArrayList();
    private ObservableList<String> allCustomers = FXCollections.observableArrayList();
    private ObservableList<String> allRentals = FXCollections.observableArrayList();

    // --- 3. THE UI LINKS (Mapped directly to your fx:id tags in the .fxml file) ---
    @FXML
    private VBox splashScreenContainer;
    @FXML
    private VBox mainAppContainer;
    @FXML
    private ImageView splashLogo;
    @FXML
    private Label splashTagline;
    @FXML
    private TextField txtGenreName;
    @FXML
    private ComboBox<String> listGenres; // Corrected to String for display compatibility

    @FXML
    private TextField txtMovieTitle;
    @FXML
    private ComboBox<String> comboMovieGenre; // Corrected to String
    @FXML
    private ComboBox<String> comboRegisteredMovies; // Corrected to String
    @FXML
    private ComboBox<String> comboRegisteredCustomers; // Corrected to String
    @FXML
    private ListView<String> listMovies; // Corrected to String

    @FXML
    private TextField txtCustomerName;
    @FXML
    private ListView<String> listCustomers; // Corrected to String

    @FXML
    private ComboBox<String> comboRentalCustomer; // Corrected to String
    @FXML
    private ComboBox<String> comboRentalMovie; // Corrected to String
    @FXML
    private ListView<String> listRentals; // Corrected to String
    @FXML
    private ComboBox<String> comboRentalGenreFilter; // Corrected to String
    @FXML
    private ComboBox<String> comboRentalBorrowed; // Corrected to String
    @FXML
    private ComboBox<String> comboRentalReturned; // Corrected to String

    // --- AUTOMATIC NETWORK & CINEMATIC INITIALIZATION LOAD ---
    @FXML
    public void initialize() {
        // Force the text fields to explicitly write white text programmatically to solve typing invisibility
        txtGenreName.setStyle("-fx-text-fill: white; -fx-background-color: #1a1a24;");
        txtMovieTitle.setStyle("-fx-text-fill: white; -fx-background-color: #1a1a24;");
        txtCustomerName.setStyle("-fx-text-fill: white; -fx-background-color: #1a1a24;");

        // --- SECTION A: CONNECT TO CENTRAL RMI SERVER ---
        try {
            System.out.println("Client UI connecting to centralized RMI Server Registry...");

            // Locate the local loopback network registry running on port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote proxy stub via its verified identifier name
            remoteService = (RemoteRentalService) registry.lookup("VlsRentalService");
            System.out.println("Network link established! Remote service stub successfully assigned.");

        } catch (Throwable t) {
            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("DISTRIBUTED NETWORK ATTACHMENT ERROR:");
            t.printStackTrace();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        }

        // --- SECTION B: KICK OFF NETFLIX-STYLE CINEMATIC ANIMATIONS ---

        // 1. Zoom in for CineMaps logo
        ScaleTransition scaleLogo = new ScaleTransition(Duration.seconds(5.5), splashLogo);
        scaleLogo.setFromX(0.6);
        scaleLogo.setFromY(0.6);
        scaleLogo.setToX(1.1);
        scaleLogo.setToY(1.1);

        // 2. Configure the smooth Fade-In for your Tagline Text
        FadeTransition fadeInTagline = new FadeTransition(Duration.seconds(2.0), splashTagline);
        fadeInTagline.setFromValue(0.0);
        fadeInTagline.setToValue(1.0);

        // Play both splash elements together smoothly
        ParallelTransition splashAnimation = new ParallelTransition(scaleLogo, fadeInTagline);
        splashAnimation.play();

        // 3. Trigger the crossover transition once the intro finishes (at 5.5 seconds)
        splashAnimation.setOnFinished(event -> {
            // Fade out the splash screen overlay layer smoothly
            FadeTransition fadeOutSplash = new FadeTransition(Duration.seconds(0.8), splashScreenContainer);
            fadeOutSplash.setFromValue(1.0);
            fadeOutSplash.setToValue(0.0);

            // Fade in your gorgeous dark-mode workspace dashboard
            FadeTransition fadeInMain = new FadeTransition(Duration.seconds(0.8), mainAppContainer);
            fadeInMain.setFromValue(0.0);
            fadeInMain.setToValue(1.0);

            fadeOutSplash.setOnFinished(e -> {
                // Drop the splash screen from memory calculations entirely
                splashScreenContainer.setVisible(false);
            });

            fadeOutSplash.play();
            fadeInMain.play();

            // Populate and sync data dropdown arrays via RMI network streams once visible
            refreshAllUILists();
        });
    }

    // Helper method to sync our JavaFX ObservableLists with the database via the remote server stub
    private void refreshAllUILists() {
        try {
            if (remoteService == null) return; // Prevent crashes if RMI is offline

            // Clear local client memory window buckets to prevent duplicate stacking
            allGenres.clear();
            allMovies.clear();
            allCustomers.clear();
            allRentals.clear();

            // Map fresh data primitives via RMI loops to populate layout layers cleanly
            for (Genre g : remoteService.getAllGenres()) {
                allGenres.add(g.getName());
            }
            for (Movie m : remoteService.getAllMovies()) {
                allMovies.add(m.getTitle() + " (" + m.getGenre().getName() + ")");
            }
            for (Customer c : remoteService.getAllCustomers()) {
                allCustomers.add(c.getFullName());
            }
            for (Rental r : remoteService.getAllRentals()) {
                allRentals.add(r.getCustomer().getFullName() + " rented " + r.getMovie().getTitle());
            }

            // Push the synchronized data directly into your visual UI components
            listGenres.setItems(allGenres);
            comboMovieGenre.setItems(allGenres);
            comboRegisteredMovies.setItems(allMovies);
            comboRentalMovie.setItems(allMovies);
            comboRegisteredCustomers.setItems(allCustomers);
            comboRentalCustomer.setItems(allCustomers);

            // Map fresh genre data to the column filter menu
            comboRentalGenreFilter.setItems(allGenres);

            // Populate the active tracking lists
            comboRentalBorrowed.setItems(allRentals);
            comboRentalReturned.setItems(allRentals);

            if (listMovies != null) listMovies.setItems(allMovies);
            if (listCustomers != null) listCustomers.setItems(allCustomers);
            if (listRentals != null) listRentals.setItems(allRentals);

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
                Genre newGenre = new Genre(name);
                remoteService.saveGenre(newGenre);
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
        String selectedGenreName = comboMovieGenre.getValue();

        if (!title.isEmpty() && selectedGenreName != null) {
            try {
                Genre targetGenre = new Genre(selectedGenreName);
                Movie newMovie = new Movie(title, targetGenre);
                remoteService.saveMovie(newMovie);
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
                Customer newCustomer = new Customer(fullName);
                remoteService.saveCustomer(newCustomer);
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
        String customerStr = comboRentalCustomer.getValue();
        String movieStr = comboRentalMovie.getValue();

        if (customerStr != null && movieStr != null) {
            try {
                // Parse out clean matching constructs for backend delivery
                Customer c = new Customer(customerStr);
                String rawMovieTitle = movieStr.contains(" (") ? movieStr.substring(0, movieStr.indexOf(" (")) : movieStr;
                Movie m = new Movie(rawMovieTitle, new Genre("Unknown"));

                Rental newRental = new Rental(c, m);
                remoteService.saveRental(newRental);
                comboRentalCustomer.setValue(null);
                comboRentalMovie.setValue(null);
                refreshAllUILists();
            } catch (Exception e) {
                System.err.println("RMI transmission error on leasing transaction processing:");
                e.printStackTrace();
            }
        }
    }
}