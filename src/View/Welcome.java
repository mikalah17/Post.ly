package View;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


import Controller.CreateUser;
import model.Database;
import model.User;

public class Welcome {
    
    public Welcome(Database database) {
        JFrame frame = new JFrame();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.add(new JLabel("Post.ly", 40, GUIConstants.black, Font.BOLD), BorderLayout.NORTH);
        
        JPanel center = new JPanel(new GridLayout(6, 1, 10, 10));
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));
        JTextField username = new JTextField ("Username");
        center.add(username);
        JTextField password = new JTextField ("Password");
        center.add(password);
        JTextField confirmPassword = new JTextField ("Confirm Password");
        center.add(confirmPassword);
        JButton createAcc = new JButton("Create Account", 45, 20);
        
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
					u = create.getUser();
					new Alert("Account created succesfully, ID: "+u.getID(), frame);
				} else {
					new Alert("This email has been used before", frame);
        		}
        }
        }
        
        );	
        	
        
        center.add(createAcc);
        
        panel.add(center, BorderLayout.CENTER);
        
        JLabel login = new JLabel ("Already have an account? Login", 20, GUIConstants.black, Font.BOLD);
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
			new Login(database);
			frame.dispose();
			}
        });
    
        
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login, BorderLayout.SOUTH);
        
        frame.getContentPane().add(panel);
        
        frame.setVisible(true);
        frame.requestFocus();
        
    } 
}
