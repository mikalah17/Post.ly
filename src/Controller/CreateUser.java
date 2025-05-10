package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Database;
import model.User; 
import View.Alert;

public class CreateUser {
	
	private User u;
	private Database database;


	public CreateUser(User u, Database database) {
		this.u = u;
		this.database = database;
	}
	
	public void create () {
		String insert = "INSERT INTO users(username, password) VALUES (?, ?)";
	    try {
	        PreparedStatement stmt = database.getConnection().prepareStatement(insert);
	        stmt.setString(1, u.getusername());
	        stmt.setString(2, u.getpassword()); // Stores plaintext password
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        new Alert(e.getMessage(), null);
	    }
	}
	public boolean isusernameUsed() {
		String select = "SELECT * FROM `users` WHERE `username` = '"+u.getusername()+"';";
		boolean used = false;
		try {
		ResultSet rs = database.getStatement().executeQuery(select);
		used = rs.next();
		} catch (SQLException e) {
			new Alert(e.getMessage(), null);
		}
		return used;
	}

	public User getUser() {
		String select = "SELECT ID FROM users WHERE username = '"+u.getusername()+"' AND password = '"+u.getpassword()+"';";
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			rs.next();
			u.setID(rs.getInt("ID"));
		} catch (SQLException e) {
			new Alert(e.getMessage(), null);
		}
		return u;
	}

	
	}	
	
	
	

