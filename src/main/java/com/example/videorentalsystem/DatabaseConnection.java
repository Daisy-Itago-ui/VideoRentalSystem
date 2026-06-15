package com.example.videorentalsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // These credentials point directly to the MariaDB/MySQL vault we built together
    private static final String URL = "jdbc:mysql://localhost:3306/video_rental_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Keep this empty as verified during login

    public static Connection getConnection() throws SQLException {
        try {
            // Tells Java to look for the MySQL driver package
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL/MariaDB Driver not found!", e);
        }
    }
}