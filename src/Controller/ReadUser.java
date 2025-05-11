package Controller;

import java.sql.*;
import java.util.Arrays;
import model.Database;
import model.User;
import View.Alert;

public class ReadUser {
    private boolean loggedIn = false;
    private User user;
    
    public ReadUser(String username, char[] password, Database database) {
        try {
            String sql = "SELECT id, username, password, is_admin, is_active FROM users WHERE username = ?";
            PreparedStatement stmt = database.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                if (!rs.getBoolean("is_active")) {
                    new Alert("This account is deactivated", null);
                    this.loggedIn = false;
                    Arrays.fill(password, '\0'); // Clear password
                    return;
                }
                
                // Get stored password and compare
                String storedPassword = rs.getString("password");
                boolean passwordMatches = comparePasswords(password, storedPassword);
                
                if (passwordMatches) {
                    this.loggedIn = true;
                    this.user = new User();
                    user.setID(rs.getInt("id"));
                    user.setusername(username);
                } else {
                    this.loggedIn = false; // 👈 ADD THIS
                    new Alert("Incorrect username or password", null);
                }
            } else {
                new Alert("Username not found", null);
            }
            
            // Clear password from memory
            Arrays.fill(password, '\0');
        } catch (SQLException e) {
            new Alert("Database error", null);
        }
    }
    
    private boolean comparePasswords(char[] enteredPassword, String storedPassword) {
        // Convert char[] to String for comparison
        String entered = new String(enteredPassword);
        boolean matches = entered.equals(storedPassword);
        
        // Clear the temporary String
        entered = "";
        
        return matches;
    }
    
    public boolean loggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user;
    }
}