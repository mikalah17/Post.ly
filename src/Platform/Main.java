package Platform;

import View.Welcome;
import model.Database;

public class Main {
	
	public static void main(String[] args) {
		new Welcome(new Database());
	}

}
