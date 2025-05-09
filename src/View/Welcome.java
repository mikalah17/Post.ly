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
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Welcome {
    
    public Welcome(Database database) {
        JFrame frame = new JFrame();
        
        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));
        panel.setLayout(null);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("C:\\Users\\minie\\eclipse-workspace\\postly\\src\\View\\logo.jpg"));
        label.setText("");
        label.setBounds(84, 53, 732, 66);
        panel.add(label);
       
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
					u = create.getUser();
					new Alert("Account created succesfully, ID: "+u.getID(), frame);
				} else {
					new Alert("This email has been used before", frame);
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
			new Login(database);
			frame.dispose();
			}
        });
    
        
        login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.setHorizontalAlignment(JLabel.CENTER);
        panel.add(login);
        
        frame.getContentPane().add(panel);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\minie\\eclipse-workspace\\postly\\src\\View\\bg.jpg"));
        lblNewLabel.setBounds(0, 0, 900, 625);
        panel.add(lblNewLabel);
        
        frame.setVisible(true);
        frame.requestFocus();
        
    } 
}
