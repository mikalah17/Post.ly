package View.admin.panels;

import javax.swing.*;
import java.awt.*;
import model.admin.AdminStats;
import View.GUIConstants;

public class StatsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
    private static final Color CARD_BG = new Color(240, 240, 240);
    
    public StatsPanel(AdminStats stats) {
        super(new GridLayout(2, 2, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(GUIConstants.background);
        
        add(createStatCard("Total Users", stats.getTotalUsers(), Color.decode("#4CAF50")));
        add(createStatCard("Active Users", stats.getActiveUsers(), Color.decode("#2196F3")));
        add(createStatCard("Total Posts", stats.getTotalPosts(), Color.decode("#FF9800")));
        add(createStatCard("Reported Posts", stats.getReportedPosts(), Color.decode("#F44336")));
    }

    private JPanel createStatCard(String title, int value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(GUIConstants.black);
        
        JLabel valueLabel = new JLabel(String.valueOf(value), JLabel.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
}