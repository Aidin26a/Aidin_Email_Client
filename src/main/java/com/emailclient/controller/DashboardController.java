package com.emailclient.controller;

import com.emailclient.model.EmailMessage;
import com.emailclient.service.EmailService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private TableView<EmailMessage> emailTableView;
    @FXML private TableColumn<EmailMessage, String> senderColumn;
    @FXML private TableColumn<EmailMessage, String> subjectColumn;
    @FXML private WebView emailWebView;
    @FXML private ProgressIndicator loadingSpinner;

    private EmailService emailService = new EmailService();
    private String userEmail, userPass;

    public void setCredentials(String email, String pass) {
        this.userEmail = email;
        this.userPass = pass;
        loadEmails();
    }

    @FXML
    public void initialize() {
        // Table setup
        senderColumn.setCellValueFactory(cellData -> cellData.getValue().senderProperty());
        subjectColumn.setCellValueFactory(cellData -> cellData.getValue().subjectProperty());

        emailTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Renders the HTML content properly in the WebView
                emailWebView.getEngine().loadContent(newVal.getContent());
            }
        });
    }

    private void loadEmails() {
        // Show the spinner before starting the background task
        loadingSpinner.setVisible(true);

        new Thread(() -> {
            try {
                var emails = emailService.fetchEmails(userEmail, userPass);

                Platform.runLater(() -> {
                    emailTableView.setItems(emails);
                    // Hide the spinner once the emails are in the table
                    loadingSpinner.setVisible(false);
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    loadingSpinner.setVisible(false);
                    new Alert(Alert.AlertType.ERROR, "Failed to load emails: " + e.getMessage()).show();
                });
            }
        }).start();
    }

    @FXML
    private void onComposeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emailclient/compose.fxml"));
            Parent root = loader.load();

            ComposeController controller = loader.getController();
            controller.setSenderCredentials(this.userEmail, this.userPass);

            Stage stage = new Stage();
            stage.setTitle("Compose New Email");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emailclient/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) emailTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Aidin Email Client");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClose() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onDeleteClick() {
        EmailMessage selected = emailTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            emailTableView.getItems().remove(selected);
            emailWebView.getEngine().loadContent("");
        }
    }
}