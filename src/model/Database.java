package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            this.connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
        }        
    }

    // For regular queries
    public Statement getStatement() {
        return statement;
    }

    // For prepared statements (used in ReadUser)
    public Connection getConnection() {
        return connection;
    }
}