package com.webapp.repository;

import com.webapp.model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcUserRepository implements UserRepository {

    // Simple environment-variable based configuration
    private Connection getConnection() throws Exception {
        String url = System.getenv("DB_URL");
        if (url == null)
            url = "jdbc:mysql://localhost:3306/webapp";

        String user = System.getenv("DB_USER");
        if (user == null)
            user = "root";

        String pass = System.getenv("DB_PASS");
        if (pass == null)
            pass = "prashi123";

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (name, phone, email, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Using PreparedStatement prevents SQL Injection!
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
    }

    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Database error", e);
        }
    }
}
