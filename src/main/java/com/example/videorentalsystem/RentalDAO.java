package com.example.videorentalsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    // 1. SAVE: Inserts a brand new rental transaction into your MariaDB ledger table
    public static void saveRental(Rental rental) {
        String query = "INSERT INTO rentals (client_id, movie_id, returned) VALUES (?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, rental.getCustomer().getId());
            pstmt.setInt(2, rental.getMovie().getId());
            pstmt.setBoolean(3, rental.isReturned());

            pstmt.executeUpdate();
            System.out.println("Rental transaction successfully logged in database vault!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🌟 2. READ: Reconstructs historical transactions from database via Object Composition Joins
    public static List<Rental> getAllRentals() {
        List<Rental> rentalsList = new ArrayList<>();

        // This query performs a 3-way join to pull raw records and attach strings cleanly
        String query = "SELECT r.id AS rental_id, r.returned, " +
                "c.id AS client_id, c.fullname, " +
                "m.id AS movie_id, m.title " +
                "FROM rentals r " +
                "INNER JOIN clients c ON r.client_id = c.id " +
                "INNER JOIN movies m ON r.movie_id = m.id;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // a. Reconstruct Customer Object
                int clientId = rs.getInt("client_id");
                String fullName = rs.getString("fullname");
                Customer customer = new Customer(clientId, fullName);

                // b. Reconstruct Movie Object (Passing null for genre since title suffices for rental receipt text)
                int movieId = rs.getInt("movie_id");
                String movieTitle = rs.getString("title");
                Movie movie = new Movie(movieId, movieTitle, null);

                // c. Reconstruct complete Rental Ledger Object
                int rentalId = rs.getInt("rental_id");
                boolean returned = rs.getBoolean("returned");

                Rental rental = new Rental(rentalId, customer, movie, returned);
                rentalsList.add(rental);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalsList;
    }
}
