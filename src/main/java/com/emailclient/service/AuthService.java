package com.emailclient.service;

import com.emailclient.model.User;

public class AuthService {
    public boolean login(String username, String password) {
        // Future: Add SQLite credential verification here
        return true;
    }

    public void register(User newUser) {
        // Logic to save hashed password to SQLite
}