package View.admin.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminUIUtils {

    // Standard admin button styling
    public static JButton createAdminButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighter(bgColor, 0.2f));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    // Standard admin panel styling
    public static JPanel createAdminPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return panel;
    }

    // Standard admin table styling
    public static void styleAdminTable(JTable table) {
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 5));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionBackground(new Color(220, 240, 255));
    }

    // Create a standardized section header
    public static JLabel createSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        return label;
    }

    // Create a confirmation dialog with admin styling
    public static boolean showConfirmDialog(Component parent, String message) {
        Object[] options = {"Confirm", "Cancel"};
        int result = JOptionPane.showOptionDialog(
            parent,
            message,
            "Admin Confirmation",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]
        );
        return result == 0;
    }

    // Color utility for hover effects
    private static Color brighter(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        
        int i = (int)(1.0/(1.0-factor));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i);
        }
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;

        return new Color(
            Math.min((int)(r/factor), 255),
            Math.min((int)(g/factor), 255),
            Math.min((int)(b/factor), 255)
        );
    }

    // Standard admin colors
    public static class Colors {
        public static final Color PRIMARY = new Color(0, 120, 215);
        public static final Color DANGER = new Color(216, 59, 1);
        public static final Color WARNING = new Color(255, 185, 0);
        public static final Color SUCCESS = new Color(16, 124, 16);
    }

    // Standard admin dimensions
    public static class Dimensions {
        public static final Dimension BUTTON = new Dimension(120, 40);
        public static final Dimension TABLE_CELL = new Dimension(150, 30);
    }
}