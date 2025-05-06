package View.admin.panels;

import javax.swing.*;
import java.awt.*;
import Controller.admin.UserManagementController;
import View.admin.components.UserTable;

import View.GUIConstants;


public class UserManagementPanel extends JPanel {
	private static final long serialVersionUID = 1L;
    public UserManagementPanel(UserManagementController controller) {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // Search and filter controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Add search components...
        
        // User table
        UserTable userTable = new UserTable(controller.getAllUsers());
        
        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Added horizontal and vertical gaps
        actionPanel.setBackground(GUIConstants.background); // Set consistent background

        // Create ban button with proper styling
        JButton banBtn = new JButton("Ban User");
        banBtn.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Explicit font setting
        banBtn.setPreferredSize(new Dimension(120, 30)); // Better size control

        // Action handler
        banBtn.addActionListener(e -> {
            int selectedId = userTable.getSelectedUserId();
            if (selectedId != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Ban user with ID: " + selectedId + "?", 
                    "Confirm Ban", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.banUser(selectedId);
                    userTable.updateData(controller.getAllUsers());
                    JOptionPane.showMessageDialog(this, "User banned successfully");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user first");
            }
        });

        actionPanel.add(banBtn); 
        
        add(controlsPanel, BorderLayout.NORTH);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
}
