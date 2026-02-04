package com.emailclient.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import com.emailclient.model.EmailMessage;

public class InboxController {
    @FXML
    private TableView<EmailMessage> emailTable;

    public void handleViewInbox() {
        // Logic to refresh inbox via IMAP
    }

    public void handleSendEmail() {
        // Logic to open Compose window
    }
}