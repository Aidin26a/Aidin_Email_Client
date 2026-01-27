package com.emailclient.service;

import com.emailclient.model.User;

public class AuthService {
    // Sprint 1 Focus: Registration and Login logic [cite: 298]
    public boolean login(String username, String password) {
        // Future: Add SQLite credential verification here [cite: 444]
        return true;
    }

    public void register(User newUser) {
        // Logic to save hashed password to SQLite [cite: 445]
    }
}