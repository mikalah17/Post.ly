package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;

@SuppressWarnings("serial")
public class JTextArea extends javax.swing.JTextArea {
	
	private String hint;
	
	
	public JTextArea(String hint, int textSize, int padding) {
		super();
		this.hint = hint;
		setFont(new Font("Segoe UI", Font.BOLD, textSize));
		setBackground(null);
		setText(hint);
		setForeground(GUIConstants.textAreaHint);
		setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (getText().equals("")) {
					setText(hint);
					setForeground(GUIConstants.textAreaHint);
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
	
	public JTextArea(String text, int textSize, Color color, int style) {
		super();
		setFont(new Font("Segoe UI", style, textSize));
		setText(text);
		setForeground(color);
		setEditable(false);
		setPreferredSize(new Dimension(1000, (int) getPreferredSize().getHeight()));
		
	
	}
	public boolean isEmpty() {
		return getText().equals(hint) || getText().equals("");
	}

	
	}


