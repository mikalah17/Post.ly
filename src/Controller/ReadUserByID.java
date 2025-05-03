package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import View.Alert;
import model.Database;
import model.User;

public class ReadUserByID {

	private User user;
	
	public ReadUserByID(int ID, Database database) {
		String select = "SELECT * FROM `users` WHERE `id` ="+ID+" ;";
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			rs.next();
			user = new User();
			user.setID(ID);
			user.setusername(rs.getString("Username"));
			
		} catch (SQLException e) {
			new Alert(e.getMessage(), null);
		}
	}
	
	public User getUser() {
		return user;
	}
}
