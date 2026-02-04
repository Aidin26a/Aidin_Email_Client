package com.emailclient.view;
import com.emailclient.model.User;

public class EmailClient {
    private static EmailClient instance;
    private User currentUser;

    private EmailClient() {}

    public static EmailClient getInstance() {
        if (instance == null) {
            instance = new EmailClient();
        }
        return instance;
    }

    // Handles core operations
    public void login(String email, String password) { /* Auth logic */ }
}