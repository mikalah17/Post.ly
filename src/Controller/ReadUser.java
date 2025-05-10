package Controller;

import java.sql.*;
import model.Database;
import model.User;
import View.Alert;

public class ReadUser {
    private boolean loggedIn;
    private User user;
    
    public ReadUser(String username, String password, Database database) {
        try {
            Connection conn = database.getConnection();
            String sql = "SELECT id, username, password, is_admin FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                
                // Direct string comparison (no hashing)
                if (password.equals(storedPassword)) {
                    this.loggedIn = true;
                    this.user = new User();
                    user.setID(rs.getInt("id"));
                    user.setusername(username);
                } else {
                    new Alert("Incorrect password", null);
                }
            } else {
                new Alert("Username not found", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert("Database error", null);
        }
    }
    
    public boolean loggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user;
    }
}