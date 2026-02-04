package com.emailclient.service;

import java.sql.*;

public class DatabaseService {
    private static final String URL = "jdbc:sqlite:emailclient.db";

    public DatabaseService() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // SQL to create the users table if it's missing
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "email TEXT UNIQUE, "
                + "password TEXT"
                + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Database Init Error: " + e.getMessage());
        }
    }

    public void saveUser(String email, String password) {
        String sql = "INSERT OR REPLACE INTO users(email, password) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println("Credentials saved to SQLite.");
        } catch (SQLException e) {
            System.err.println("Save User Error: " + e.getMessage());
        }
    }

    // Helpful for "Auto-Login" feature
    public String[] getSavedUser() {
        String sql = "SELECT email, password FROM users LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return new String[]{rs.getString("email"), rs.getString("password")};
            }
        } catch (SQLException e) {
            System.err.println("Fetch User Error: " + e.getMessage());
        }
        return null;
    }
}