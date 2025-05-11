package View;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import Controller.AdminController;
import model.Database;
import model.User;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class AdminDashboard {
    private JFrame frame;
    private Database database;
    private User adminUser;
    private AdminController adminController;
    private JPanel cardsPanel;
    private CardLayout cardLayout;
    private JPanel userManagementPanel; // Add this
    private JPanel contentPanel; // Add this


    public AdminDashboard(User adminUser, Database database) {
    	this.adminController = new AdminController(database);
        this.adminUser = adminUser;
        this.database = database;
        
        frame = new JFrame();
        frame.setTitle("Admin Dashboard - Post.ly");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main panel with sidebar and content
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Sidebar
        JPanel sideBar = createSidebar();
        mainPanel.add(sideBar, BorderLayout.WEST);
        
        // Content area with CardLayout
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        
        // Create panels
        this.userManagementPanel = createUserManagementPanel();
        this.contentPanel = createContentPanel();
        
        cardsPanel.add(createDashboardPanel(), "dashboard");
        cardsPanel.add(userManagementPanel, "users");
        cardsPanel.add(contentPanel, "content");
        
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sideBar = new JPanel();
        sideBar.setBackground(new Color(44, 62, 80));
        sideBar.setPreferredSize(new Dimension(250, 700));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        
        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(52, 73, 94));
        header.setPreferredSize(new Dimension(250, 100));
        JLabel title = new JLabel("Admin Panel");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        header.add(title);
        sideBar.add(header);
        
        // Menu items
        sideBar.add(Box.createVerticalStrut(20));
        addSidebarButton(sideBar, "Dashboard", "dashboard");
        addSidebarButton(sideBar, "User Management", "users");
        addSidebarButton(sideBar, "Content Moderation", "content");
        sideBar.add(Box.createVerticalGlue());
        addSidebarButton(sideBar, "Logout", "logout");
        
        return sideBar;
    }

    private void addSidebarButton(JPanel sidebar, String text, String action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 50));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (action.equals("logout")) {
                    frame.dispose();
                    new Login(database);
                } else {
                    cardLayout.show(cardsPanel, action);
                }
            }
        });
        
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        
        try {
            int userCount = adminController.getAllUsers(0, 0).size();
            int activeUsers = adminController.getActiveUserCount();
            int flaggedPosts = adminController.getFlaggedPostCount();
            
            statsPanel.add(createStatCard("Total Users", String.valueOf(userCount), new Color(46, 204, 113)));
            statsPanel.add(createStatCard("Active Users", String.valueOf(activeUsers), new Color(52, 152, 219)));
            statsPanel.add(createStatCard("Flagged Posts", String.valueOf(flaggedPosts), new Color(231, 76, 60)));
            statsPanel.add(createStatCard("Admin Privileges", "Enabled", new Color(155, 89, 182)));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading statistics: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        panel.add(statsPanel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("User Management", JLabel.LEFT), BorderLayout.WEST);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshUserList();
            }
        });
        header.add(refreshBtn, BorderLayout.EAST);
        
        panel.add(header, BorderLayout.NORTH);
        panel.add(new JScrollPane(createUserList()), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel header = new JPanel(new BorderLayout());
        header.add(new JLabel("Content Moderation", JLabel.LEFT), BorderLayout.WEST);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshFlaggedPosts();
            }
        });
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

        JLabel userLabel = new View.JLabel(
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
                        String status = user.isActive() ? "deactivated" : "activated";
                        new Alert("User " + status + " successfully", frame);
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
    
    private JPanel createPostEntryWithFlagging(model.Post post) {
        JPanel entry = new JPanel(new BorderLayout());
        entry.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Post content
        JTextArea content = new JTextArea(post.getContent());
        content.setEditable(false);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        
        // Flag indicator if flagged
        if (post.isFlagged()) {
            JLabel flagLabel = new JLabel("FLAGGED: " + post.getFlagReason());
            flagLabel.setForeground(Color.RED);
            flagLabel.setFont(flagLabel.getFont().deriveFont(Font.BOLD));
            entry.add(flagLabel, BorderLayout.NORTH);
        }
        
        // Post metadata
        JPanel metaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        metaPanel.add(new JLabel("Posted by: @" + post.getUser().getusername()));
        metaPanel.add(new JLabel(" | "));
        metaPanel.add(new JLabel(post.getDateTime().toString()));
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        if (!post.isFlagged()) {
            JButton flagBtn = new JButton("Flag");
            flagBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    flagPost(post);
                }
            });
            buttonPanel.add(flagBtn);
        } else {
            JButton approveBtn = new JButton("Approve");
            approveBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    approvePost(post);
                }
            });
            buttonPanel.add(approveBtn);
            
            JButton deleteBtn = new JButton("Delete");
            deleteBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    deletePost(post);
                }
            });
            buttonPanel.add(deleteBtn);
        }
        
        entry.add(content, BorderLayout.CENTER);
        entry.add(metaPanel, BorderLayout.SOUTH);
        entry.add(buttonPanel, BorderLayout.EAST);
        
        return entry;
    }

    private void flagPost(model.Post post) {
        FlagPostDialog dialog = new FlagPostDialog(frame, post.getID());
        dialog.setVisible(true);
        
        if (dialog.isConfirmed() && !dialog.getReason().isEmpty()) {
            try {
                if (adminController.flagPost(post.getID(), dialog.getReason(), adminUser.getID())) {
                    new Alert("Post flagged successfully", frame);
                    refreshFlaggedPosts();
                }
            } catch (SQLException ex) {
                new Alert("Failed to flag post: " + ex.getMessage(), frame);
            }
        }
    }

    private void approvePost(model.Post post) {
        try {
            if (adminController.moderatePost(post.getID(), "approve")) {
                new Alert("Post approved and made visible", frame);
                refreshFlaggedPosts();
            }
        } catch (SQLException ex) {
            new Alert("Failed to approve post: " + ex.getMessage(), frame);
        }
    }

    private void deletePost(model.Post post) {
        try {
            if (adminController.moderatePost(post.getID(), "delete")) {
                new Alert("Post deleted successfully", frame);
                refreshFlaggedPosts();
            }
        } catch (SQLException ex) {
            new Alert("Failed to delete post: " + ex.getMessage(), frame);
        }
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
    
    public boolean flagPost(int postId, String reason, int reporterId) throws SQLException {
        String query = "UPDATE postlets SET is_flagged = TRUE, flagged_reason = ?, flagged_by = ? WHERE id = ?";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            stmt.setString(1, reason);
            stmt.setInt(2, reporterId);
            stmt.setInt(3, postId);
            return stmt.executeUpdate() > 0;
        }
    }

    public int getFlaggedPostCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM postlets WHERE is_flagged = TRUE";
        try (PreparedStatement stmt = database.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}