package View;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


import Controller.CreateUser;
import model.Database;
import model.User;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Welcome {
    
    public Welcome(Database database) {
        JFrame frame = new JFrame();
        
        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.setLayout(null);
        try {
            Image logoImage = ImageIO.read(getClass().getResource("/View/images/logo.jpg"));
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoLabel.setBounds(84, 53, 732, 66);
            panel.add(logoLabel);
        } catch (IOException e) {
            System.err.println("Error loading logo: " + e.getMessage());
            // Fallback to text logo
            panel.add(new View.JLabel("Post.ly", 24, GUIConstants.blue, Font.BOLD));
        }
       
        JTextField username = new JTextField ("Username");
        username.setBounds(310, 166, 270, 54);
        panel.add(username);
        JTextField password = new JTextField ("Password");
        password.setBounds(310, 230, 270, 54);
        panel.add(password);
        JTextField confirmPassword = new JTextField ("Confirm Password");
        confirmPassword.setBounds(310, 294, 270, 54);
        panel.add(confirmPassword);
        JButton createAcc = new JButton("Create Account", 45, 20);
        createAcc.setBounds(310, 390, 270, 54);
        
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
			public void mouseClicked(MouseEvent e) {
        		if (username.isEmpty()) {
        			new Alert("Username cannot be empty", frame);
        			return;
        	}   
        		if (password.isEmpty()) {
        			new Alert("Password cannot be empty", frame);
        			return;
        	}
        		if(password.getText().length()<6) {
        			new Alert("Password must contain at least 6 characters", frame);
        			return;
        		};
        	
        		if (confirmPassword.isEmpty()) {
        			new Alert("Please confirm password", frame);
        			return;
        		}
        		if (!password.getText().equals(confirmPassword.getText())) {
        			new Alert("Password doesn't match", frame);
        			return;
			}
        		User u = new User();
        		u.setusername(username.getText());
        		u.setpassword(password.getText());
        		
        		CreateUser create = new CreateUser(u, database);
				if (!create.isusernameUsed()) {
					create.create();
					frame.dispose(); 
					u = create.getUser();
					new Alert("Account created succesfully, ID: " + u.getID(), frame);
					frame.dispose();
					new Login(database);
				} else {
					new Alert("This email/username has been used before", frame);
        		}
        }
        }
        
        );	
        	
        
        panel.add(createAcc);
        
        JLabel login = new JLabel ("Already have an account?");
        login.setFont(new Font("Segoe UI", Font.BOLD, 20));
        login.setBounds(84, 522, 732, 27);
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
			frame.dispose();
			new Login(database);
			}
        });
    
        
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login);
        
        frame.getContentPane().add(panel);
        
        try {
            Image bgImage = ImageIO.read(getClass().getResource("/View/images/bg.jpg"));
            JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
            bgLabel.setBounds(0, 0, 900, 625);
            panel.add(bgLabel, Integer.valueOf(0));
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
            // Fallback to solid color
            panel.setBackground(GUIConstants.background);
        }
        
        frame.setVisible(true);
        frame.requestFocus();
        
    } 
}
