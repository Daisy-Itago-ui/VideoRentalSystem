package com.example.videorentalsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public static void saveGenre(Genre genre) {
        // Keeps 'genres' table, uses lowercase column 'genre'
        String query = "INSERT INTO genres (genre, isactive) VALUES (?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Restored your original method name
            pstmt.setString(1, genre.getName());
            pstmt.setBoolean(2, true);

            pstmt.executeUpdate();
            System.out.println("Genre saved to database vault successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Genre> getAllGenres() {
        List<Genre> genresList = new ArrayList<>();
        String query = "SELECT id, genre FROM genres WHERE isactive = 1;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                // Pulls out of your lowercase 'genre' column
                String name = rs.getString("genre");

                // Restored your original constructor usage
                Genre genre = new Genre(id, name);
                genresList.add(genre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genresList;
    }
}