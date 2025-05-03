package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import View.Alert;
import model.Database;
import model.User;

public class ReadAllUsers {
	
	private ArrayList<User> users;
	
	public ReadAllUsers(Database database, User user) {
		String select = "SELECT * FROM `users`;";
		users = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while(rs.next()) {
				User u = new User();
				u.setID(rs.getInt("ID"));
				u.setusername(rs.getString("Username"));
				if (u.getID()!=user.getID()) users.add(user);
				
			}
		} catch(SQLException e) {
			new Alert(e.getMessage(), null);
		}
	}
	
	public ArrayList<User> getList() {
		return users;
	}

}
