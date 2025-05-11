package View;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class JTextField extends javax.swing.JTextField {
    private static final long serialVersionUID = 1L;
    private Shape shape;
    private String hint;
    private int radius = 15; // Match your password field radius
    
    public JTextField(String hint) {
        super();
        this.hint = hint; 
        setFont(new Font("Segoe UI", Font.PLAIN, 18)); // More standard font weight
        setOpaque(false);
        setText(hint);
        setForeground(GUIConstants.textFieldHint);
        setBorder(new EmptyBorder(10, 15, 10, 15)); // Better padding
        setBackground(Color.WHITE); // Set background color
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(hint);
                    setForeground(GUIConstants.textFieldHint);
                }
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setForeground(GUIConstants.black);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw rounded background
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Draw rounded border
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(GUIConstants.textAreaHint); // Lighter border color
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        }
        return shape.contains(x, y);
    }

    public boolean isEmpty() {
        return getText().isEmpty() || getText().equals(hint);
    }
    
    // Remove getPassword() as it's not needed for username field
}