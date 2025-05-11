package Controller;

import java.sql.*;
import model.Database;
import model.Post;
import View.Alert;

public class CreatePost {
    private Post post;
    private Database database;
    
    public CreatePost(Post post, Database database) {
        this.post = post;
        this.database = database;
    }
    
    public boolean posted() {
        // Ensure the database connection is open before proceeding
        try {
            if (database.getConnection() == null || database.getConnection().isClosed()) {
                // Re-establish the connection if it was closed
                System.out.println("Re-establishing database connection...");
                database.checkAndReestablishConnection(); // Call the method to re-establish the connection
            }
        } catch (SQLException e) {
            new Alert("Error with the database connection: " + e.getMessage(), null);
            return false;
        }
        
        String insert = "INSERT INTO postlets(content, user, created_at) VALUES (?, ?, ?)";
        
        try (Connection connection = database.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(insert)) {
            
            // Set parameters
            stmt.setString(1, post.getContent());
            stmt.setInt(2, post.getUser().getID());
            stmt.setTimestamp(3, Timestamp.valueOf(post.getDateTime()));
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            new Alert("Posting failed: " + e.getMessage(), null);
            e.printStackTrace();
            return false;
        }
    }
}
