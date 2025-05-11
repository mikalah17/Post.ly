package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RoundedPasswordField extends JPasswordField {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
    private int radius = 15;
    private Timer maskTimer;
    private final int showDuration = 1000; // 1 second visibility
    private StringBuilder realPassword = new StringBuilder();
    private boolean selfUpdating = false;

    public RoundedPasswordField(String hint) {
        super(hint);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 15, 10, 15));
        setFont(new Font("Segoe UI", Font.PLAIN, 18));
        setForeground(GUIConstants.textAreaHint);
        setEchoChar('•'); // Start with masked input

        // Timer to mask the last character
        maskTimer = new Timer(showDuration, e -> {
            if (realPassword.length() > 0) {
                setEchoChar('•');
                updateDisplay(false);
            }
        });
        maskTimer.setRepeats(false);

        // Document listener to handle text changes
        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if (selfUpdating) return;
                
                try {
                    String newText = getText();
                    if (newText.length() > realPassword.length()) {
                        // Character was added
                        char newChar = newText.charAt(newText.length()-1);
                        realPassword.append(newChar);
                        
                        selfUpdating = true;
                        setEchoChar((char)0); // Show last character
                        updateDisplay(true);
                        maskTimer.restart();
                        selfUpdating = false;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (selfUpdating) return;
                
                if (realPassword.length() > 0) {
                    realPassword.deleteCharAt(realPassword.length()-1);
                    updateDisplay(false);
                }
            }

            public void changedUpdate(DocumentEvent e) {}
        });

        // Handle focus changes
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (realPassword.length() == 0) {
                    setText("");
                    setForeground(GUIConstants.black);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (realPassword.length() == 0) {
                    setText(hint);
                    setForeground(GUIConstants.textAreaHint);
                }
                setEchoChar('•');
            }
        });
    }

    private void updateDisplay(boolean showLastChar) {
        if (selfUpdating) return;
        
        selfUpdating = true;
        char[] display = new char[realPassword.length()];
        Arrays.fill(display, '•');
        
        if (showLastChar && realPassword.length() > 0) {
            // Show last character temporarily
            display[display.length-1] = realPassword.charAt(realPassword.length()-1);
        }
        
        // Safe text update
        SwingUtilities.invokeLater(() -> {
            setText(new String(display));
            selfUpdating = false;
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(GUIConstants.textAreaHint);
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

    @Override
    public char[] getPassword() {
        return realPassword.toString().toCharArray();
    }

    public boolean isEmpty() {
        return realPassword.length() == 0;
    }
}