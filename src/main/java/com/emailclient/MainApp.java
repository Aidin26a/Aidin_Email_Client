package com.emailclient;

import com.emailclient.controller.DashboardController;
import com.emailclient.database.DatabaseManager;
import com.emailclient.service.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseService db = new DatabaseService();
        String[] credentials = db.getSavedUser();

        if (credentials != null) {
            // Auto-login: Go straight to Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emailclient/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setCredentials(credentials[0], credentials[1]);
            stage.setScene(new Scene(root));
        } else {
            // No saved user: Show Login
            Parent root = FXMLLoader.load(getClass().getResource("/com/emailclient/login.fxml"));
            stage.setScene(new Scene(root));
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}