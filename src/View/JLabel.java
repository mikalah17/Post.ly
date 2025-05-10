package View;

import java.awt.Color; 
import java.awt.Font;

public class JLabel extends javax.swing.JLabel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JLabel(String text, int textSize, Color color, int style) {
        setFont(new Font("Segoe UI", style, textSize));
        setText(text);
        setForeground(color);
        
    }
    
}