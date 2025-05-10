package View;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Controller.ReadAllUsers;
import model.Database;
import model.User;


public class CustomView {
	
	public CustomView(String view, User user, Database database) {
		new JFrame();
		
		JPanel panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(null);
		
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(GUIConstants.white);
		Dimension dimension = new Dimension (500, 50);
		header.setPreferredSize(dimension);
		header.setMaximumSize(dimension);
		header.setMinimumSize(dimension);
		header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		panel.add(header);
		switch(view) {
		case "Users":
			ArrayList<User> users = new ReadAllUsers(database, user).getList();
			for (@SuppressWarnings("unused") User u : users) {
				panel.add(Box.createVerticalStrut(7));
				
			}
			break;
		}
		
	}

}
