// AdminDashboard.java (in View package)
package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import Controller.AdminController;
import model.Database;
import model.User;

public class AdminDashboard {
    private JFrame frame;
    private AdminController adminController;
    
    public AdminDashboard(User adminUser, Database database) {
        this.adminController = new AdminController(database);
        frame = new JFrame("Admin Dashboard - Post.ly");
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Users Management Tab
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("User Management", usersPanel);
        
        // Content Moderation Tab
        JPanel contentPanel = createContentPanel();
        tabbedPane.addTab("Content Moderation", contentPanel);
        
        frame.getContentPane().add(tabbedPane);
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add user management components here
        // ...
        
        return panel;
    }
    
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add content moderation components here
        // ...
        
        return panel;
    }
}