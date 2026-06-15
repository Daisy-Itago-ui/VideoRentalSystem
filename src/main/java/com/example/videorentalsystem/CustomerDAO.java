package com.example.videorentalsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // 1. SAVE: Adds a new client row to the vault
    public static void saveCustomer(Customer customer) {
        // MATCHED TO YOUR DB: Using lowercase 'fullname' table column name
        String query = "INSERT INTO clients (fullname, isactive) VALUES (?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Keeps your original method call intact
            pstmt.setString(1, customer.getFullName());
            pstmt.setBoolean(2, true); // Active by default

            pstmt.executeUpdate();
            System.out.println("Client saved to database vault successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ: Fetches all active clients from the database
    public static List<Customer> getAllCustomers() {
        List<Customer> customersList = new ArrayList<>();
        // MATCHED TO YOUR DB: Querying lowercase 'fullname'
        String query = "SELECT id, fullname FROM clients WHERE isactive = 1;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                // MATCHED TO YOUR DB: Reading from lowercase 'fullname' column key
                String fullName = rs.getString("fullname");

                // Keeps your original constructor usage intact
                Customer customer = new Customer(id, fullName);
                customersList.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }
}