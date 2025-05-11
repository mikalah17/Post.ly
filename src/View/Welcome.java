package View;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;

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
            java.net.URL logoUrl = getClass().getResource("/View/images/logo.jpg");
            if (logoUrl != null) {
                Image logoImage = ImageIO.read(logoUrl);
                JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
                logoLabel.setBounds(50, 30, 150, 50); // Top-left position
                panel.add(logoLabel);
            } else {
                // Fallback to text logo in same position
                JLabel textLogo = new View.JLabel("Post.ly", 24, GUIConstants.blue, Font.BOLD);
                textLogo.setBounds(20, 20, 150, 50);
                panel.add(textLogo);
            }
        } catch (IOException e) {
            System.err.println("Error loading logo: " + e.getMessage());
            // Text fallback if image fails
            JLabel textLogo = new View.JLabel("Post.ly", 24, GUIConstants.blue, Font.BOLD);
            textLogo.setBounds(20, 20, 150, 50);
            panel.add(textLogo);
        }

        // Center the form components below the logo
        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setBounds(0, 100, 900, 54); // Positioned below logo
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title);
       
        JTextField username = new JTextField ("Username");
        username.setBounds(310, 166, 270, 54);
        panel.add(username);
        
        RoundedPasswordField password = new RoundedPasswordField("Password");
        password.setBounds(310, 230, 270, 54);
        password.setBackground(GUIConstants.white);
        panel.add(password);

        RoundedPasswordField confirmPassword = new RoundedPasswordField("Confirm Password");
        confirmPassword.setBounds(310, 294, 270, 54);
        confirmPassword.setBackground(GUIConstants.white);
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
		        char[] passwordChars = password.getPassword();
		        char[] confirmChars = confirmPassword.getPassword();
		        
		        // Check if username is empty
		        if (username.getText().trim().isEmpty() || username.getText().equals("Username")) {
		            new Alert("Username cannot be empty", frame);
		            Arrays.fill(passwordChars, '\0');
		            Arrays.fill(confirmChars, '\0');
		            return;
		        }   
		        
		        // Check if password is empty
		        if (passwordChars.length == 0 ) {
		            new Alert("Password cannot be empty", frame);
		            Arrays.fill(passwordChars, '\0');
		            Arrays.fill(confirmChars, '\0');
		            return;
		        }
		        
		        if (passwordChars.length < 6) {
		            new Alert("Password must contain at least 6 characters", frame);
		            Arrays.fill(passwordChars, '\0');
		            Arrays.fill(confirmChars, '\0');
		            return;
		        }
		        
		        // Check if confirm password is empty
		        if (confirmChars.length == 0 || new String(confirmChars).equals("Confirm Password")) {
		            new Alert("Please confirm password", frame);
		            Arrays.fill(passwordChars, '\0');
		            Arrays.fill(confirmChars, '\0');
		            return;
		        }
		        
		        if (!Arrays.equals(passwordChars, confirmChars)) {
		            new Alert("Password doesn't match", frame);
		            Arrays.fill(passwordChars, '\0');
		            Arrays.fill(confirmChars, '\0');
		            return;
		        }
		        
		        User u = new User();
		        u.setusername(username.getText());
		        u.setpassword(new String(passwordChars)); // Only convert when needed
		        
		        CreateUser create = new CreateUser(u, database);
		        if (!create.isusernameUsed()) {
		            create.create();
		            frame.dispose(); 
		            u = create.getUser();
		            new Alert("Account created successfully, ID: " + u.getID(), frame);
		            new Login(database);
		        } else {
		            new Alert("This username has been used before", frame);
		        }
		        
		        // Clear password data from memory
		        Arrays.fill(passwordChars, '\0');
		        Arrays.fill(confirmChars, '\0');
		    }
        });	
        	
        
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
