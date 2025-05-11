package Platform;

import java.sql.SQLException;
import java.util.ArrayList;

import Controller.AdminController;
import View.Login;
import model.Database;
import model.User;

public class Main {
	
	public static void main(String[] args) throws SQLException {
		new Login(new Database());
		Database database = new Database();
        AdminController adminController = new AdminController(database);

        // Simulate admin login, perform some operations
        System.out.println("Admin logged in.");
        ArrayList<User> users = adminController.getAllUsers(10, 0);
        // Do something with users

        // Now log out
        adminController.logout();  // Close connection and reset

        // Simulate a new login after logout
        System.out.println("Admin logged out, now logging in again.");
        adminController = new AdminController(new Database());  // New connection on login
        users = adminController.getAllUsers(10, 0);  // Perform operations with fresh connection
	}

}
