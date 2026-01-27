package com.emailclient.model;

/**
 * Represents a user in the system.
 * Maps to the Class Diagram requirements.
 */
public class User {
    private int userId;
    private String username;
    private String passwordHash; // Security Requirement N3
    private String emailAddress;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Basic authentication placeholder for Sprint 1
    public boolean authenticate() {
        return username != null && !username.isEmpty();
    }

    // Getters and Setters
    public String getUsername() { return username; }
}