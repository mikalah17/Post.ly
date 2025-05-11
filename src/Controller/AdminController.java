package Controller;

import java.sql.*;
import java.util.ArrayList;
import model.*;
import View.Alert;

public class AdminController {
    private final Database database;
    
    public AdminController(Database database) {
        this.database = database;
    }

    // Secure user listing with pagination
    public ArrayList<User> getAllUsers(int limit, int offset) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT id, username, is_admin, is_active FROM users LIMIT ? OFFSET ?";
        
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = rs.getBoolean("is_admin") ? new Admin() : new User();
                user.setID(rs.getInt("id"));
                user.setusername(rs.getString("username"));
                user.setActive(rs.getBoolean("is_active"));
                users.add(user);
            }
        }
        return users;
    }

    // Secure status toggling
    public boolean toggleUserStatus(int userId) throws SQLException {
        String query = "UPDATE users SET is_active = NOT is_active WHERE id = ?";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Flagged posts with reasons
    public ArrayList<Post> getFlaggedPosts() throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT p.*, u.username FROM postlets p JOIN users u ON p.user = u.id WHERE p.is_flagged = TRUE";
        
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setID(rs.getInt("id"));
                post.setContent(rs.getString("content"));
                post.setFlagReason(rs.getString("flagged_reason"));
                
                User author = new User();
                author.setID(rs.getInt("user"));
                author.setusername(rs.getString("username"));
                post.setUser(author);
                
                posts.add(post);
            }
        }
        return posts;
    }

    // Post moderation actions
    public boolean moderatePost(int postId, String action) throws SQLException {
        String query = action.equals("delete") ?
            "DELETE FROM postlets WHERE id = ?" :
            "UPDATE postlets SET is_flagged = FALSE WHERE id = ?";
            
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        }
    }
}