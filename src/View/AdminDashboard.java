package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.Database;
import model.User;

public class AdminDashboard {
    private JFrame frame;
    private Database database;
    private User adminUser;

    public AdminDashboard(User adminUser, Database database) {
        this.adminUser = adminUser;
        this.database = database;
        
        frame = new JFrame();
        frame.setTitle("Admin Dashboard - Post.ly");
        frame.setSize(900, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel with sidebar and content
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Sidebar (matches Home.java style)
        JPanel sideBar = createSidebar();
        mainPanel.add(sideBar, BorderLayout.WEST);
        
        // Content area
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User Management", createUserManagementPanel());
        tabbedPane.addTab("Content Moderation", createContentPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sideBar = new JPanel();
        sideBar.setBackground(GUIConstants.white);
        sideBar.setPreferredSize(new Dimension(182, 625));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        
        // Profile section
        JPanel profile = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        profile.setBackground(GUIConstants.white);
        profile.add(new JLabel("@" + adminUser.getusername(), 19, GUIConstants.black, Font.BOLD));
        
        // Menu buttons (matches Home.java style)
        sideBar.add(Box.createVerticalStrut(20));
        sideBar.add(profile);
        sideBar.add(Box.createVerticalStrut(10));
        sideBar.add(new SideButton("Dashboard", "dashboard", adminUser, database));
        sideBar.add(Box.createVerticalStrut(5));
        sideBar.add(new SideButton("User Management", "users", adminUser, database));
        sideBar.add(Box.createVerticalStrut(5));
        sideBar.add(new SideButton("Content Moderation", "moderation", adminUser, database));
        sideBar.add(Box.createVerticalStrut(5));
        sideBar.add(new SideButton("Logout", "logout", adminUser, database));
        
        return sideBar;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        panel.add(new JLabel("User Management", 24, GUIConstants.blue, Font.BOLD));
        panel.add(Box.createVerticalStrut(20));
        
        // User list (you'll populate this from your controller)
        JScrollPane userListScroll = new JScrollPane(createUserList());
        panel.add(userListScroll);
        
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        panel.add(new JLabel("Content Moderation", 24, GUIConstants.blue, Font.BOLD));
        panel.add(Box.createVerticalStrut(20));
        
        // Flagged posts list
        JScrollPane contentScroll = new JScrollPane(createFlaggedPostsList());
        panel.add(contentScroll);
        
        return panel;
    }

    private JPanel createUserList() {
        // This would be populated from your AdminController
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Example user entries
        panel.add(createUserEntry("user1", false));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createUserEntry("user2", true));
        
        return panel;
    }

    private JPanel createUserEntry(String username, boolean isActive) {
        JPanel entry = new JPanel(new BorderLayout());
        entry.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        entry.setBackground(GUIConstants.white);
        
        JLabel userLabel = new JLabel("@" + username, 16, GUIConstants.black, Font.PLAIN);
        JButton toggleBtn = new JButton(isActive ? "Deactivate" : "Activate", 30, 14);
        
        toggleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Call AdminController to toggle user status
                new Alert("Status updated for @" + username, frame);
            }
        });
        
        entry.add(userLabel, BorderLayout.WEST);
        entry.add(toggleBtn, BorderLayout.EAST);
        return entry;
    }

    private JPanel createFlaggedPostsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Example flagged posts
        panel.add(createFlaggedPostEntry("Inappropriate content"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFlaggedPostEntry("Spam detected"));
        
        return panel;
    }

    private JPanel createFlaggedPostEntry(String reason) {
        JPanel entry = new JPanel(new BorderLayout());
        entry.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        entry.setBackground(GUIConstants.white);
        
        JLabel postLabel = new JLabel("Post #123 - " + reason, 14, GUIConstants.black, Font.PLAIN);
        JButton reviewBtn = new JButton("Review", 30, 14);
        
        reviewBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Call AdminController to review post
                new Alert("Reviewing flagged post", frame);
            }
        });
        
        entry.add(postLabel, BorderLayout.WEST);
        entry.add(reviewBtn, BorderLayout.EAST);
        return entry;
    }
}