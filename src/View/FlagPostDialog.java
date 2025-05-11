package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class FlagPostDialog extends JDialog {
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
        flagButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirmed = true;
                dispose();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(flagButton);
        
        panel.add(new JLabel("Reason for flagging:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(reasonField), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getReason() {
        return reasonField.getText().trim();
    }
}