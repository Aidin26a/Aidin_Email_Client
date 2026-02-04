package com.emailclient.controller;

import com.emailclient.service.EmailService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class ComposeController {
    @FXML private TextField recipientField;
    @FXML private TextField subjectField;
    @FXML private TextArea bodyArea;
    @FXML private Label attachmentLabel;

    private EmailService emailService = new EmailService();
    private File selectedFile;

    // These must be set when the Dashboard opens this window
    private String userEmail;
    private String userPass;

    // Helper method to receive credentials from Dashboard
    public void setSenderCredentials(String email, String pass) {
        this.userEmail = email;
        this.userPass = pass;
    }

    @FXML
    private void onAttachClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Attach");
        selectedFile = fileChooser.showOpenDialog(recipientField.getScene().getWindow());

        if (selectedFile != null) {
            // Update the label so the user sees the file name
            attachmentLabel.setText("Attached: " + selectedFile.getName());
            attachmentLabel.setStyle("-fx-text-fill: green;");
            System.out.println("File selected: " + selectedFile.getName());
        } else {
            attachmentLabel.setText("No file attached");
            attachmentLabel.setStyle("-fx-text-fill: #777777;");
        }
    }

    @FXML
    private void onSendClick() {
        // 1. Get text from the UI fields
        String to = recipientField.getText();
        String sub = subjectField.getText();
        String body = bodyArea.getText();

        // 2. Validation: Ensure we have a recipient and user credentials
        if (to.isEmpty() || userEmail == null || userPass == null) {
            new Alert(Alert.AlertType.ERROR, "Recipient or sender credentials missing!").show();
            return;
        }

        // 3. Background Thread: Prevents the UI from freezing while sending
        new Thread(() -> {
            try {
                // Requirement F3: Send with the attachment we picked earlier
                emailService.sendEmail(userEmail, userPass, to, sub, body, selectedFile);

                Platform.runLater(() -> {
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Email Sent Successfully!");
                    success.showAndWait();
                    onCancelClick(); // Closes the compose window
                });
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert error = new Alert(Alert.AlertType.ERROR, "Send Failed: " + e.getMessage());
                    error.show();
                });
            }
        }).start();

        Platform.runLater(() -> {
            new Alert(Alert.AlertType.INFORMATION, "Email Sent!").show();
            attachmentLabel.setText("No file attached"); // Reset label
            selectedFile = null; // Clear the file reference
            onCancelClick();
        });
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) recipientField.getScene().getWindow();
        stage.close();
    }


}