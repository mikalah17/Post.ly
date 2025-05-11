package View;

import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import Controller.AdminController;
import Controller.CreatePost;
import Controller.GenerateTimeline;
import model.Database;
import model.User;

public class Home {
	
	public Home(User user, Database database) {
		ArrayList<model.Post> posts = new GenerateTimeline(user, database).getPosts();
		AdminController adminController = new AdminController(database);
		JFrame frame = new JFrame(); 
		frame.getContentPane().setLayout(new BorderLayout());
		
				
		JPanel sideBar = new JPanel();
		sideBar.setBackground(GUIConstants.white);
		Dimension sideBarDim = new Dimension(182, 1000);
		sideBar.setPreferredSize(sideBarDim);
		sideBar.setMaximumSize(sideBarDim);
		sideBar.setMinimumSize(sideBarDim);
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
		sideBar.add(Box.createVerticalStrut(20));
		
		JPanel profile = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		profile.setMaximumSize(new Dimension(182, 50));
		profile.setBackground(GUIConstants.white);
		
		profile.add(new javax.swing.JLabel("@" + user.getusername()) {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
		        setFont(new Font("Segoe UI", Font.BOLD, 22)); // Change the font size here
		    }
		});
		
		sideBar.add(profile);
		sideBar.add(new SideButton("Posts", "myposts", user, database));
		sideBar.add(Box.createVerticalStrut(3));
		sideBar.add(new SideButton("Users", "allusers", user, database));
		sideBar.add(Box.createVerticalStrut(3));
		sideBar.add(Box.createVerticalStrut(300)); // Add some space
		sideBar.add(new SideButton("Logout", "logout", user, database));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(null);
				
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(GUIConstants.white);
		Dimension dimension = new Dimension(500, 159);
		header.setPreferredSize(dimension);
		header.setMinimumSize(dimension);
		header.setMaximumSize(dimension);
		header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		JPanel north = new JPanel(new BorderLayout());
		north.setBackground(null);
		north.add(new JLabel("Home", 20, GUIConstants.black, Font.BOLD), BorderLayout.WEST);
		
		header.add(north, BorderLayout.NORTH);
		
		JTextArea postIn = new JTextArea("Drop a postlet?", 18, 20);
		header.add(new JScrollPane(postIn), BorderLayout.CENTER);
		
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		south.setBackground(null);
		
		JButton postBtn = new JButton("Post", 35, 16);
		postBtn.addMouseListener(new MouseListener() {
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
				if (postIn.isEmpty()) {
					new Alert("Cannot publish empty post", frame);
				return;
			}
				model.Post post = new model.Post(postIn.getText(), user);
				
				if (new CreatePost(post, database).posted()) {
					new Alert("Posted Succesfully", frame);
					postIn.setText("");
					
				}
			}
			});
	
		
		postBtn.setPreferredSize(new Dimension(81, 37));
		south.add(postBtn);
		header.add(south, BorderLayout.SOUTH);
		
		panel.add(header);
		
		
		for (int i = 0; i < posts.size(); i++) {
		    panel.add(Box.createVerticalStrut(7));
		    panel.add(new Post(
		        posts.get(i),    // The post model
		        frame,           // Parent JFrame
		        user,            // Current user
		        adminController  // Admin controller for flagging
		    ));
		}
		
		frame.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		frame.getContentPane().add(Box.createHorizontalStrut(182), BorderLayout.EAST);
		
		frame.getContentPane().add(sideBar, BorderLayout.WEST);
		frame.setVisible(true);
		frame.requestFocus();
		
	}
	
}
