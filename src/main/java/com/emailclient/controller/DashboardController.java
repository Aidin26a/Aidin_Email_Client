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
    @FXML private WebView emailWebView; // Requirement F1: Upgrade to WebView for HTML rendering

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

        // Requirement F1: Selection Listener to Read Emails
        emailTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Renders the HTML content properly in the WebView
                emailWebView.getEngine().loadContent(newVal.getContent());
            }
        });
    }

    private void loadEmails() {
        // Requirement N5: Run network tasks in a background thread
        new Thread(() -> {
            try {
                var emails = emailService.fetchEmails(userEmail, userPass);
                Platform.runLater(() -> emailTableView.setItems(emails));
            } catch (Exception e) {
                e.printStackTrace();
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
            // Load the Login screen (Requirement F2)
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
            // Requirement F1: Clear the view after deletion
            emailWebView.getEngine().loadContent("");
        }
    }
}