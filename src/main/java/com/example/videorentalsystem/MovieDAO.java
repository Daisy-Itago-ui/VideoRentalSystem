package com.example.videorentalsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    // 1. SAVE: Inserts a new movie title along with its matched genre ID barcode
    public static void saveMovie(Movie movie) {
        // MATCHED TO YOUR DB: Using lowercase 'title' table column name
        String query = "INSERT INTO movies (title, genre_id, isactive) VALUES (?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Restored your original method structure
            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getGenre().getId());
            pstmt.setBoolean(3, true); // Active by default

            pstmt.executeUpdate();
            System.out.println("Movie saved to database vault successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. READ: Pulls movie records and reconstructs their composition relationships
    public static List<Movie> getAllMovies() {
        List<Movie> moviesList = new ArrayList<>();

        // MATCHED TO YOUR DB: Using lowercase 'm.title' to read your table structure
        String query = "SELECT m.id AS movie_id, m.title, g.id AS genre_id, g.genre AS genre_name " +
                "FROM movies m " +
                "INNER JOIN genres g ON m.genre_id = g.id " +
                "WHERE m.isactive = 1;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // a. Reconstruct the internal Genre object first
                int genreId = rs.getInt("genre_id");
                String genreName = rs.getString("genre_name");
                Genre genre = new Genre(genreId, genreName);

                // b. Extract the movie details using lowercase 'title' to match your database precisely
                int movieId = rs.getInt("movie_id");
                String title = rs.getString("title");

                // c. Assemble the complete Movie object cleanly using your original constructor layout
                Movie movie = new Movie(movieId, title, genre);
                moviesList.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moviesList;
    }
}