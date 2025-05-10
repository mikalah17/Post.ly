package View;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Controller.ReadUser;
import model.Database;
import model.User;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Login {
	
	public Login(Database database) {
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(115, 0, 182, 0));
		panel.setLayout(null);
		
		
		JLabel title = new JLabel("Login");
		title.setFont(new Font("Segoe UI", Font.BOLD, 40));
		title.setBounds(0, 115, 900, 54);
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title);
		
		JTextField username = new JTextField("Username");
		username.setBounds(315, 196, 270, 58);
		panel.add(username);
		JTextField password = new JTextField("Password");
		password.setBounds(315, 264, 270, 58);
		panel.add(password);
		JButton login = new JButton("Login", 45, 20);
		login.setBounds(315, 348, 270, 58);
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
				public void mouseClicked(MouseEvent e) {
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
				        // Add admin check
				        String adminCheck = "SELECT is_admin FROM users WHERE username = '" + username.getText() + "'";
				        try {
				            ResultSet rs = database.getStatement().executeQuery(adminCheck);
				            if (rs.next() && rs.getBoolean("is_admin")) {
				                new AdminDashboard(user, database);
				            } else {
				            	frame.dispose();
				                new Home(user, database);
				            }
				        } catch(SQLException ex) {
				            new Alert(ex.getMessage(), frame);
				        }
				    } else {
				        new Alert("Incorrect username or password", frame);
				    }
				}
			});
		panel.add(login);
		
		JLabel createAcc = new JLabel("Don't have an account? Create new one");
		createAcc.setFont(new Font("Segoe UI", Font.BOLD, 20));
		createAcc.setBounds(0, 416, 900, 27);
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
					frame.dispose(); 
					new Welcome(database);
				}
				});
		createAcc.setCursor(new Cursor(Cursor.HAND_CURSOR));
		createAcc.setHorizontalAlignment(JLabel.CENTER);
		panel.add(createAcc);
		
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\minie\\eclipse-workspace\\postly\\src\\View\\bg.jpg"));
		lblNewLabel.setBounds(0, 0, 900, 625);
		panel.add(lblNewLabel);
		frame.setVisible(true);
		frame.requestFocus();
		
		try {
            Image bgImage = ImageIO.read(getClass().getResource("/View/images/bg.jpg"));
            View.JLabel bgLabel = new View.JLabel("", 0, null, 0); // Empty custom JLabel
            bgLabel.setIcon(new ImageIcon(bgImage));
            bgLabel.setBounds(0, 0, 900, 625);
            panel.add(bgLabel, Integer.valueOf(0));
        } catch (IOException e) {
            System.err.println("Error loading background: " + e.getMessage());
            panel.setBackground(GUIConstants.background);
        }
		
	}
	
}
