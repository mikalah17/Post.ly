package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font; 
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.ReadUser;
import model.Database;
import model.User;

public class Login {
	
	public Login(Database database) {
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(115, 0, 182, 0));
		
		
		JLabel title = new JLabel("Login", 40, GUIConstants.black, Font.BOLD);
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
		center.setBackground(null);
		center.setBorder(BorderFactory.createEmptyBorder(34, 315, 17, 315));
		JTextField username = new JTextField("Username");
		center.add(username);
		JTextField password = new JTextField("Password");
		center.add(password);
		JButton login = new JButton("Login", 45, 20);
		login.addMouseListener(new MouseListener() {
	        	@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) 	{
				if (username.isEmpty()) {
					new Alert("Username cannot be empty", frame);
					return;
				}
				if (password.isEmpty()) {
					new Alert("Password cannot be empty", frame);
					return;
				}
				
				ReadUser read = new ReadUser(username.getText(), password.getText(), database);
				if (read.loggedIn()) {
					User user = read.getUser();
					new Home(user, database);
				} else {
					new Alert("Incorrect username or password", frame);
				}
				}	
				});
		center.add(login);
		
		panel.add(center, BorderLayout.CENTER);
		
		JLabel createAcc = new JLabel("Don't have an account? Create new one", 20, GUIConstants.black, Font.BOLD);
		 createAcc.addMouseListener(new MouseListener() {
	        	@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) 	{
					new Welcome(database);
				}
				});
		createAcc.setCursor(new Cursor(Cursor.HAND_CURSOR));
		createAcc.setHorizontalAlignment(JLabel.CENTER);
		panel.add(createAcc, BorderLayout.SOUTH);
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.requestFocus();
		
	}

}
