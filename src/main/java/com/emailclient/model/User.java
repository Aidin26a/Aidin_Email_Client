package com.emailclient.model;

import java.sql.*;

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

    public static class DatabaseService {
        private static final String URL = "jdbc:sqlite:emailclient.db";

        public DatabaseService() {
            initializeDatabase();
        }

        private void initializeDatabase() {
            // Requirement F4: Create the users table if it doesn't exist
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "email TEXT UNIQUE, "
                    + "password TEXT"
                    + ");";
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("Database initialized and table created.");
            } catch (SQLException e) {
                System.err.println("Database init error: " + e.getMessage());
            }
        }

        public void saveUser(String email, String password) {
            // INSERT OR REPLACE ensures we don't get duplicate emails in our local DB
            String sql = "INSERT OR REPLACE INTO users(email, password) VALUES(?, ?)";
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                System.out.println("User credentials saved to SQLite.");
            } catch (SQLException e) {
                System.err.println("Error saving user: " + e.getMessage());
            }
        }
    }
}