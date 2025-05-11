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
            String sql = "SELECT id, username, password, is_admin, is_active FROM users WHERE username = ?";
            PreparedStatement stmt = database.getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                if (!rs.getBoolean("is_active")) {
                    new Alert("This account is deactivated", null);
                    this.loggedIn = false;  // Explicitly set to false
                    return;
                }
                
                if (password.equals(rs.getString("password"))) {
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