package com.emailclient.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneService {
    public static void changeScene(Stage currentStage, String fxmlFile, String title) {
        try {
            // Updated path logic to be more reliable
            // In SceneService.java, change the line to this:
            // In SceneService.java
            // Replace the loader line in SceneService.java with this:
            FXMLLoader loader = new FXMLLoader(SceneService.class.getResource("/com/emailclient/dashboard.fxml"));

            if (loader.getLocation() == null) {
                // This will tell us EXACTLY where it is looking so we can fix the folder
                System.out.println("DEBUG: Looking for FXML at: " + SceneService.class.getResource(""));
                throw new IOException("FXML file not found! Check your folder structure.");
            }

            Parent root = loader.load();
            currentStage.setTitle(title);
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }}