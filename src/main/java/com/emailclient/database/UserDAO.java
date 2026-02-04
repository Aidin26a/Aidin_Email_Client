package com.emailclient.database;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void registerUser(String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users(email, password) VALUES(?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);

            pstmt.executeUpdate();
            System.out.println("User registered and secured with BCrypt!");
        } catch (SQLException e) {
            System.out.println("Registration Database Error: " + e.getMessage());
        }
    }

    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                // This compares the plain text password with the stored BCrypt hash
                return BCrypt.checkpw(password, storedHash);
            }
        } catch (SQLException e) {
            System.out.println("Login Database Error: " + e.getMessage());
        }
        return false;
    }
}