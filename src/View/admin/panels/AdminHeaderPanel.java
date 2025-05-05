package View.admin.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.User;
import View.GUIConstants;
import View.JLabel;

public class AdminHeaderPanel extends JPanel {
    public AdminHeaderPanel(User adminUser, ActionListener logoutAction) {
        super(new BorderLayout());
        setBackground(GUIConstants.white);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Title
        JLabel title = new JLabel("Admin Dashboard", 24, GUIConstants.black, Font.BOLD);
        add(title, BorderLayout.WEST);
        
        // Admin info and logout
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(null);
        
        JLabel adminLabel = new JLabel("Logged in as: " + adminUser.getusername(), 
            16, GUIConstants.blue, Font.PLAIN);
        
        JButton logoutBtn = createLogoutButton(logoutAction);
        
        rightPanel.add(adminLabel);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(logoutBtn);
        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createLogoutButton(ActionListener logoutAction) {
        JButton button = new JButton("Logout");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(GUIConstants.blue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(logoutAction);
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(GUIConstants.blue.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(GUIConstants.blue);
            }
        });
        
        return button;
    }
}