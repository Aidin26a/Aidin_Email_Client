package com.emailclient.controller;

import com.emailclient.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!email.isEmpty() && !password.isEmpty()) {
            // Save to database for next time (Requirement F4)
            User.DatabaseService db = new User.DatabaseService();
            db.saveUser(email, password);

            proceedToDashboard(email, password);
        } else {
            statusLabel.setText("Please enter credentials.");
        }
    }

    private void proceedToDashboard(String email, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emailclient/dashboard.fxml"));
            Parent root = loader.load();
            DashboardController controller = loader.getController();
            controller.setCredentials(email, password);

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}