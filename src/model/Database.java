package model;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection connection;
    private Statement statement;
    private final String url = "jdbc:mysql://localhost/postly";
    private final String user = "root";
    private final String pass = "";

    public Database() {
        try {
            // 1. Establish connection
            this.connection = DriverManager.getConnection(url, user, pass);
            
            // 2. Create statement
            this.statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            
            System.out.println("Database connected successfully");
            DriverManager.setLogWriter(null);

        } catch (SQLException e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
        }        
    }
    public void checkAndReestablishConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Re-establishing database connection...");
                connection = DriverManager.getConnection(url, user, pass);
            }
        } catch (SQLException e) {
            System.out.println("Error with database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // For regular queries
    public Statement getStatement() {
        return statement;
    }

    // For prepared statements (used in ReadUser)
    public Connection getConnection() {
        checkAndReestablishConnection();
        return connection;
    }

    // Method to fetch all users from the database with pagination
    public ArrayList<User> getAllUsers(int limit, int offset) {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users LIMIT ? OFFSET ?"; // SQL query to fetch users with pagination

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, limit);  // Set limit
            stmt.setInt(2, offset);  // Set offset

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Assuming user table has columns: id, username, is_active
                int id = rs.getInt("id");
                String username = rs.getString("username");
                boolean isActive = rs.getBoolean("is_active");

                // Assuming User class has a constructor with these parameters
                User user = new User(id, username, isActive);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }

    // Method to fetch a user by ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);  // Set the user ID parameter

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                boolean isActive = rs.getBoolean("is_active");

                return new User(id, username, isActive);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by ID: " + e.getMessage());
        }
        return null; // Return null if user is not found
    }

    // Method to add a new user to the database
    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, is_active) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getusername());  // Set username
            stmt.setBoolean(2, user.isActive());    // Set active status

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if user was successfully added
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
        return false; // Return false if there was an error
    }

    // Method to update the user's status (active/inactive)
    public boolean updateUserStatus(int userId, boolean isActive) {
        String query = "UPDATE users SET is_active = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, isActive);  // Set active status
            stmt.setInt(2, userId);        // Set user ID

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if user status was successfully updated
        } catch (SQLException e) {
            System.out.println("Error updating user status: " + e.getMessage());
        }
        return false;
    }

    // Method to delete a user by ID
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);  // Set user ID

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if user was successfully deleted
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection.");
            e.printStackTrace();
        }
    }
}
