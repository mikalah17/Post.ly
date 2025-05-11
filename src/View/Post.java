package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

import Controller.AdminController;
import model.User;

public class Post extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame parentFrame;
    private User currentUser;
    private AdminController adminController;
    public Post(model.Post post, JFrame parentFrame, User currentUser, AdminController adminController) {
        this.parentFrame = parentFrame;
        this.currentUser = currentUser;
        this.adminController = adminController;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GUIConstants.white);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 25));
        
        // Header with author and date
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(null);
        
        JLabel author = new JLabel("@" + post.getUser().getusername(), 20, GUIConstants.post, Font.BOLD);
        header.add(author, BorderLayout.WEST);
        
        JLabel date = new JLabel(post.getDateToString(), 15, GUIConstants.post, Font.PLAIN);
        header.add(date, BorderLayout.EAST);
        
        add(header);
        add(Box.createVerticalStrut(7));
        
        // Post content
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEADING));
        center.setBackground(null);
        JTextArea content = new JTextArea(post.getContent(), 18, GUIConstants.post, Font.PLAIN);
        center.add(content);
        add(center);
        add(Box.createVerticalStrut(7));
        
        // Flag button (if not admin view)
        if (currentUser != null && !post.isFlagged()) {
            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            footer.setBackground(null);
            
            JButton flagBtn = new JButton("Flag");
            flagBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    FlagPostDialog dialog = new FlagPostDialog(parentFrame, post.getID());
                    dialog.setVisible(true);
                    
                    if (dialog.isConfirmed()) {
                        try {
                            if (adminController.flagPost(
                                post.getID(),
                                dialog.getReason(),
                                currentUser.getID()
                            )) {
                                new Alert("Post flagged successfully", parentFrame);
                            }
                        } catch (SQLException ex) {
                            new Alert("Failed to flag post: " + ex.getMessage(), parentFrame);
                        }
                    }
                }
            });
            footer.add(flagBtn);
            add(footer);
        }
        
        // Set preferred size based on content
        int height = (int) (155 + content.getPreferredSize().getHeight());
        Dimension dimension = new Dimension(500, height);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }
    }
