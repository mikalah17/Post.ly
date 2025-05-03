package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import View.Alert;
import model.Database;
import model.User;

public class ReadUser {
	
	private boolean loggedIn;
	private User user;
	
	public ReadUser(String username, String password, Database database) {
		String select = "SELECT * FROM `users` WHERE `username` = '"+username+"' AND `password` = '"+password+"';";
	try {
		ResultSet rs = database.getStatement().executeQuery(select);
		loggedIn = rs.next();
		if (loggedIn) {
			user = new User();
			user.setID(rs.getInt("ID"));
			user.setusername(rs.getString("Username"));
			user.setpassword(rs.getString("Password"));
		}
	} catch (SQLException e) {
		new Alert(e.getMessage(), null);
	}
	}
	public boolean loggedIn() {
		return loggedIn;
	
			
		}
	

	public User getUser() {
		return user;
	}

}
