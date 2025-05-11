package View;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RoundedPasswordField extends JPasswordField {

	private static final long serialVersionUID = 1L;
	private Shape shape;
    private int radius = 15;
    private Timer maskTimer;
    private final int showDuration = 1000; // 1 second visibility
    private StringBuilder visiblePassword = new StringBuilder();

    public RoundedPasswordField(String hint) {
        super(hint);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 15, 10, 15));
        setFont(new Font("Segoe UI", Font.PLAIN, 18));
        setForeground(GUIConstants.textAreaHint);
        setEchoChar('•'); // Start with masked input

        // Timer to mask the last character
        maskTimer = new Timer(showDuration, e -> {
            if (visiblePassword.length() > 0) {
                // Mask the last character
                visiblePassword.setCharAt(visiblePassword.length()-1, '•');
                setEchoChar('•');
                setText(new String(visiblePassword));
            }
        });
        maskTimer.setRepeats(false);

        // Key listener for character input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                maskTimer.stop();
                
                // Handle backspace
                if (e.getKeyChar() == '\b') {
                    if (visiblePassword.length() > 0) {
                        visiblePassword.deleteCharAt(visiblePassword.length()-1);
                    }
                    setEchoChar('•');
                    setText(new String(visiblePassword));
                    return;
                }
                
                // Add new character temporarily visible
                visiblePassword.append(e.getKeyChar());
                
                // Show all characters masked except last one
                char[] displayChars = new char[visiblePassword.length()];
                Arrays.fill(displayChars, '•');
                displayChars[displayChars.length-1] = e.getKeyChar();
                setEchoChar((char)0); // Show actual character temporarily
                setText(new String(displayChars));
                
                maskTimer.start();
            }
        });

        // Handle focus changes
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (visiblePassword.length() == 0) {
                    setText("");
                    setForeground(GUIConstants.black);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (visiblePassword.length() == 0) {
                    setText(hint);
                    setForeground(GUIConstants.textAreaHint);
                }
                setEchoChar('•');
            }
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
        // Return actual password characters (without the bullets)
        StringBuilder realPassword = new StringBuilder();
        for (int i = 0; i < visiblePassword.length(); i++) {
            if (visiblePassword.charAt(i) != '•') {
                realPassword.append(visiblePassword.charAt(i));
            }
        }
        return realPassword.toString().toCharArray();
    }

    public boolean isEmpty() {
        return visiblePassword.length() == 0;
    }
}