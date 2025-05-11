package View;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
		
		try {
	        Image logoImage = ImageIO.read(getClass().getResource("/View/images/logo.jpg")); // Update path as needed
	        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
	        logoLabel.setBounds(50, 40, 150, 50); // Adjust size/position as needed
	        panel.add(logoLabel);
	    } catch (IOException e) {
	        // Fallback to text if image fails
	        JLabel textLogo = new View.JLabel("Post.ly", 24, GUIConstants.blue, Font.BOLD);
	        textLogo.setBounds(20, 20, 150, 50);
	        panel.add(textLogo);
	    }
		
		JLabel title = new JLabel("Login");
		title.setFont(new Font("Segoe UI", Font.BOLD, 40));
		title.setBounds(0, 115, 900, 54);
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title);
		
		JTextField username = new JTextField("Username");
		username.setBounds(315, 196, 270, 58);
		panel.add(username);
		
		RoundedPasswordField password = new RoundedPasswordField("Password");
		password.setBounds(315, 265, 270, 58);
		password.setBackground(Color.WHITE);
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
		        // ===== NEW CODE START =====
		    	// In your login button's mouseClicked():
		    	char[] passwordChars = password.getPassword();
		    	ReadUser read = new ReadUser(username.getText(), passwordChars, database);
		    	Arrays.fill(passwordChars, '\0'); // Clear immediately after use
		        // ===== NEW CODE END =====
		        
		        if (username.isEmpty()) {
		            new Alert("Username cannot be empty", frame);
		            // ===== NEW CODE START =====
		            Arrays.fill(passwordChars, '\0'); // Clear password
		            // ===== NEW CODE END =====
		            return;
		        }
		        // ===== MODIFIED CODE START =====
		        if (passwordChars.length == 0) {  // Changed from password.isEmpty()
		            new Alert("Password cannot be empty", frame);
		            Arrays.fill(passwordChars, '\0'); // Clear password
		            return;
		        }
		        // ===== MODIFIED CODE END =====
		        
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
