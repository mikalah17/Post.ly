package View.admin;

import java.awt.BorderLayout;

import javax.swing.*;
import model.User;
import model.Database;
import Controller.admin.AdminController;
import View.GUIConstants;
import View.Login;
import View.admin.panels.*;

public class AdminMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    private final AdminController controller;
    private final Database database; // Store database reference
    
    public AdminMainFrame(User adminUser, Database database) {
        super("Admin Dashboard");
        this.database = database; // Initialize database reference
        this.controller = new AdminController(database);
        
        initializeUI(adminUser);
        setupWindow();
    }
    
    private void initializeUI(User adminUser) {
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GUIConstants.background);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header - pass database reference to handle logout properly
     // In AdminMainFrame.java - update the header panel creation:
        mainPanel.add(new AdminHeaderPanel(adminUser, e -> {
            this.dispose(); // Close admin window
            new Login(database); // Open login window
        }), BorderLayout.NORTH);
        
        // Tabbed content
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Users", new UserManagementPanel(controller.getUserController()));
        tabbedPane.addTab("Posts", new PostManagementPanel(controller.getPostController(), null));
        tabbedPane.addTab("Statistics", new StatsPanel(controller.getSystemStats()));
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    @SuppressWarnings("unused")
	private void handleLogout() {
        // Properly dispose current frame and show login
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            new Login(database);
        });
    }
    
    private void setupWindow() {
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}