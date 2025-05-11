package Platform;

import View.Login;
import model.Database;

public class Main {
	
	public static void main(String[] args) {
		new Login(new Database());
	}

}
