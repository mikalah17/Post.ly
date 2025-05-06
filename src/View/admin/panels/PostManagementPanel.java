package View.admin.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.admin.PostManagementController;
import View.admin.components.PostTable;
import model.admin.AdminPost;
import View.GUIConstants;

public class PostManagementPanel extends JPanel {
	private static final long serialVersionUID = 1L;
    private final PostManagementController controller;
    private final PostTable postTable;
    private JTextField searchField;
    private JComboBox<String> filterCombo;

    public PostManagementPanel(PostManagementController controller, JComboBox<String> filterCombo) {
        super(new BorderLayout());
        this.controller = controller;
        this.postTable = new PostTable(controller.getAllPosts());
		this.filterCombo = filterCombo;
        
        initializeUI();
    }

    private void initializeUI() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search and filter panel - FIXED VERSION
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(GUIConstants.background);
        
        // Make sure you're using your custom JLabel or standard JLabel properly
        searchPanel.add(new JLabel("Search:")); // Using javax.swing.JLabel
        
        this.searchField = new JTextField(20);
        searchPanel.add(searchField);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> searchPosts());
        searchPanel.add(searchBtn);
        
        searchPanel.add(new JLabel("Filter:")); // Using javax.swing.JLabel
        
        this.filterCombo = new JComboBox<>(new String[]{"All", "Flagged", "Clean"});
        filterCombo.addActionListener(e -> filterPosts());
        searchPanel.add(filterCombo);
        
        add(searchPanel, BorderLayout.NORTH);

        // Post table with scroll
        add(new JScrollPane(postTable), BorderLayout.CENTER);

        // Action buttons panel
        add(createActionButtons1(), BorderLayout.SOUTH);
    }

    private JPanel createActionButtons1() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        actionPanel.setBackground(GUIConstants.background);
        
        JButton deleteBtn = createActionButton("Delete", this::handleDelete);
        JButton flagBtn = createActionButton("Flag", this::handleFlag);
        JButton refreshBtn = createActionButton("Refresh", e -> refreshData());
        
        actionPanel.add(deleteBtn);
        actionPanel.add(flagBtn);
        actionPanel.add(refreshBtn);
        
        return actionPanel;
    }


    private JButton createActionButton(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SegoeUI", Font.BOLD, 14));
        btn.setBackground(GUIConstants.blue);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btn.addActionListener(listener);
        return btn;
    }

    private void searchPosts() {
        String query = searchField.getText().trim();
        postTable.updateData(controller.searchPosts(query));
    }

    private void filterPosts() {
        String filter = (String) filterCombo.getSelectedItem();
        postTable.updateData(controller.filterPosts(filter));
    }

    private void handleDelete(ActionEvent e) {
        AdminPost post = postTable.getSelectedPost();
        if (post != null) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete post by " + post.getUser().getusername() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deletePost(post.getID());
                refreshData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a post first");
        }
    }

    private void handleFlag(ActionEvent e) {
        AdminPost post = postTable.getSelectedPost();
        if (post != null) {
            controller.flagPost(post.getID());
            refreshData();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a post first");
        }
    }

    private void refreshData() {
        postTable.updateData(controller.getAllPosts());
    }
}