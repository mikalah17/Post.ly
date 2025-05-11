package Controller;

import model.*;
import java.sql.*;
import java.util.ArrayList;

public class AdminController {
    private Database database;

    public AdminController(Database database) {
        this.database = database;
    }

    // Fetch users with pagination
    public ArrayList<User> getAllUsers(int limit, int offset) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, limit);  // Set the limit (number of users to fetch)
            stmt.setInt(2, offset); // Set the offset (for pagination)

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password"); // Assuming you want to fetch password as well
                boolean isActive = rs.getBoolean("is_active");

                User user = new User();
                user.setID(id);
                user.setusername(username);
                user.setpassword(password); // Set the password
                user.setActive(isActive); // Set the active status

                users.add(user);
            }
        }
        return users;
    }
    
    public void logout() {
        // Close current database connection on logout
		database.close();

		// Optionally, reset session variables
		// this.currentUser = null;  // Reset current user (if applicable)

		// Re-initialize database connection on new login
		database = new Database();  // This will automatically reconnect
		System.out.println("Logged out successfully.");
    }
    // Secure user listing with pagination
    public boolean flagPost(int postId, String reason, int reporterId) throws SQLException {
        String query = "UPDATE postlets SET is_flagged = TRUE, flagged_reason = ?, flagged_by = ? WHERE id = ?";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setString(1, reason);
            stmt.setInt(2, reporterId);
            stmt.setInt(3, postId);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<Post> getFlaggedPosts() throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT p.*, u.username " +
                       "FROM postlets p " +
                       "JOIN users u ON p.user = u.id " +
                       "WHERE p.is_flagged = TRUE";
        
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setID(rs.getInt("id"));
                post.setContent(rs.getString("content"));
                post.setFlagged(true);
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


    // Secure status toggling
    public boolean toggleUserStatus(int userId) throws SQLException {
        String checkQuery = "SELECT is_active FROM users WHERE id = ?";
        try (PreparedStatement checkStmt = database.getConnection().prepareStatement(checkQuery)) {
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                boolean currentStatus = rs.getBoolean("is_active");

                // Toggle the status
                String updateQuery = "UPDATE users SET is_active = ? WHERE id = ?";
                try (PreparedStatement updateStmt = database.getConnection().prepareStatement(updateQuery)) {
                    updateStmt.setBoolean(1, !currentStatus); // Toggle the status
                    updateStmt.setInt(2, userId);
                    int affectedRows = updateStmt.executeUpdate();
                    return affectedRows > 0; // Return true if updated successfully
                }
            }
        }
        return false;
    }

    // Flagged post count
    public int getFlaggedPostCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM postlets WHERE is_flagged = TRUE";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Count active users
    public int getActiveUserCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE is_active = TRUE";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Post moderation (either delete or unflag)
    public boolean moderatePost(int postId, String action) throws SQLException {
        String query = action.equals("delete") ?
            "DELETE FROM postlets WHERE id = ?" :
            "UPDATE postlets SET is_flagged = FALSE WHERE id = ?";

        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0; // Return true if post was moderated successfully
        }
    }
}
