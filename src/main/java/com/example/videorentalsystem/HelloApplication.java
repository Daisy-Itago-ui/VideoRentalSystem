package com.example.videorentalsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Increased the size to 800x600 so your lists and buttons fit comfortably!
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Video Rental Management System");
        stage.setScene(scene);

        // CRITICAL: This is the missing line that forces the window to pop open!
        stage.show();
    }

    // Added the standard main method entry point just in case your configuration relies on it
    public static void main(String[] args) {
        launch();
    }
}