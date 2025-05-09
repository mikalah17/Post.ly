// AdminController.java (in Controller package)
package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Admin;
import model.Database;
import model.Post;
import model.User;
import View.Alert;

public class AdminController {
    private Database database;
    
    public AdminController(Database database) {
        this.database = database;
    }
    
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            ResultSet rs = database.getStatement().executeQuery(query);
            while(rs.next()) {
                User u = rs.getBoolean("is_admin") ? new Admin() : new User();
                u.setID(rs.getInt("id"));
                u.setusername(rs.getString("username"));
                users.add(u);
            }
        } catch(SQLException e) {
            new Alert(e.getMessage(), null);
        }
        return users;
    }
    
    public boolean toggleUserStatus(int userId, boolean active) {
        String query = "UPDATE users SET is_active = " + active + " WHERE id = " + userId;
        try {
            return database.getStatement().executeUpdate(query) > 0;
        } catch(SQLException e) {
            new Alert(e.getMessage(), null);
            return false;
        }
    }
    
    public ArrayList<Post> getFlaggedPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM postlets WHERE is_flagged = TRUE";
        try {
            ResultSet rs = database.getStatement().executeQuery(query);
            while(rs.next()) {
                Post p = new Post();
                p.setID(rs.getInt("id"));
                p.setContent(rs.getString("content"));
                p.setUser(new ReadUserByID(rs.getInt("user"), database).getUser());
                posts.add(p);
            }
        } catch(SQLException e) {
            new Alert(e.getMessage(), null);
        }
        return posts;
    }
}