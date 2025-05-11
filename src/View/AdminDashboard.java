package View;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.Timer;

import Controller.AdminController;
import model.Database;
import model.Post;
import model.User;

public class AdminDashboard {
    private JFrame frame;
    private Database database;
    private User adminUser;
    private final AdminController adminController;
    private JPanel userManagementPanel;  // To reference for refreshes
    private JPanel contentPanel; 

    public AdminDashboard(User adminUser, Database database) {
        this.adminController = new AdminController(database);
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
        this.userManagementPanel = createUserManagementPanel();
        this.contentPanel = createContentPanel();
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("User Management", userManagementPanel);
        tabbedPane.addTab("Content Moderation", contentPanel);
        
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
        JPanel panel = new JPanel(new BorderLayout());
        
        // Add refresh button
        View.JButton refreshBtn = new View.JButton("Refresh", 30, 14);
        refreshBtn.addMouseListener(new MouseAdapter() {
            private boolean isProcessing = false;
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isProcessing) {
                    isProcessing = true;
                    refreshBtn.setText("Refreshing...");
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            refreshUserList();
                            return null;
                        }
                        
                        @Override
                        protected void done() {
                            refreshBtn.setText("Refresh");
                            isProcessing = false;
                        }
                    }.execute();
                }
            }
        });
        
        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("User Management", 24, GUIConstants.blue, Font.BOLD), BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);
        
        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(createUserList()), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        View.JButton refreshBtn = new View.JButton("Refresh", 30, 14);
        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshFlaggedPosts();
            }
        });
        
        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Content Moderation", 24, GUIConstants.blue, Font.BOLD), BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);
        
        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(createFlaggedPostsList()), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createUserList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        try {
            ArrayList<User> users = adminController.getAllUsers(100, 0);
            for (User user : users) {
                panel.add(createUserEntry(user));
                panel.add(Box.createVerticalStrut(10));
            }
        } catch (SQLException e) {
            new Alert("Failed to load users: " + e.getMessage(), frame);
        }
        
        return panel;
    }

    private JPanel createUserEntry(User user) {
        JPanel entry = new JPanel(new BorderLayout());
        entry.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        entry.setBackground(user.isActive() ? GUIConstants.white : new Color(255, 230, 230));

        JLabel userLabel = new JLabel(
            "@" + user.getusername() + (user.isActive() ? "" : " (inactive)"), 
            16, 
            user.isActive() ? GUIConstants.black : GUIConstants.post, 
            Font.PLAIN
        );
        
        View.JButton toggleBtn = new View.JButton(user.isActive() ? "Deactivate" : "Activate", 30, 14);
        toggleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (adminController.toggleUserStatus(user.getID())) {
                        refreshUserList();
                        new Alert("User status updated", frame);
                    }
                } catch (SQLException ex) {
                    new Alert("Update failed: " + ex.getMessage(), frame);
                }
            }
        });
        
        entry.add(userLabel, BorderLayout.WEST);
        entry.add(toggleBtn, BorderLayout.EAST);
        return entry;
    }

    private JPanel createFlaggedPostsList() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        try {
            ArrayList<model.Post> posts = adminController.getFlaggedPosts();
            for (model.Post post : posts) {
                panel.add(createFlaggedPostEntry(post));
                panel.add(Box.createVerticalStrut(10));
            }
        } catch (SQLException e) {
            new Alert("Failed to load posts: " + e.getMessage(), frame);
        }
        
        return panel;
    }

    private JPanel createFlaggedPostEntry(model.Post post) {
        JPanel entry = new JPanel(new BorderLayout());
        entry.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        entry.setBackground(GUIConstants.white);
        
        JTextArea postContent = new JTextArea(
            "Post #" + post.getID() + "\n" +
            "By: @" + post.getUser().getusername() + "\n" +
            "Reason: " + post.getFlagReason() + "\n\n" +
            post.getContent(),
            5, 30
        );
        postContent.setEditable(false);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        View.JButton approveBtn = new View.JButton("Approve", 30, 14);
        approveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moderatePost(post, "approve");
            }
        });
        
        View.JButton deleteBtn = new View.JButton("Delete", 30, 14);
        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moderatePost(post, "delete");
            }
        });
        
        buttonPanel.add(approveBtn);
        buttonPanel.add(deleteBtn);
        
        entry.add(new JScrollPane(postContent), BorderLayout.CENTER);
        entry.add(buttonPanel, BorderLayout.SOUTH);
        return entry;
    }
    
    private void moderatePost(model.Post post, String action) {
        try {
            if (adminController.moderatePost(post.getID(), action)) {
                refreshFlaggedPosts();
                new Alert("Post " + (action.equals("delete") ? "deleted" : "approved"), frame);
            }
        } catch (SQLException e) {
            new Alert("Action failed: " + e.getMessage(), frame);
        }
    }
    
    private void refreshUserList() {
        Component[] comps = userManagementPanel.getComponents();
        JScrollPane scrollPane = (JScrollPane) comps[1];
        JViewport viewport = scrollPane.getViewport();
        viewport.setView(createUserList());
    }

    private void refreshFlaggedPosts() {
        Component[] comps = contentPanel.getComponents();
        JScrollPane scrollPane = (JScrollPane) comps[1];
        JViewport viewport = scrollPane.getViewport();
        viewport.setView(createFlaggedPostsList());
    }
}