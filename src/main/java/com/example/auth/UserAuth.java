package com.example.auth;
import com.example.util.Database;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserAuth {

    public boolean login(String username, String password) {
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            
            // VULNERABILITY: SQL Injection
            // The user input 'username' and 'password' are concatenated directly into the query string.
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

// Auto-generated update: 1770873415