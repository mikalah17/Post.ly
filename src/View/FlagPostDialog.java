package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class FlagPostDialog extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea reasonField;
    private boolean confirmed = false;
    
    public FlagPostDialog(JFrame parent, int postId) {
        super(parent, "Flag Post #" + postId, true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        reasonField = new JTextArea(5, 20);
        reasonField.setLineWrap(true);
        reasonField.setWrapStyleWord(true);
        
        JButton flagButton = new JButton("Flag Post");
        flagButton.addActionListener(e -> {
            if (!reasonField.getText().trim().isEmpty()) {
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a reason", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(flagButton);
        
        panel.add(new JLabel("Reason for flagging:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(reasonField), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    public String getReason() {
        return reasonField.getText().trim();
    }

    public boolean isConfirmed() {
        return confirmed && !getReason().isEmpty();
    }
}