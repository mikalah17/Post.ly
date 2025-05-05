package View;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class Alert{

	public Alert(String content, JFrame parent) {
		JFrame frame = new JFrame();
		frame.setSize(430, 170);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		ImageIcon icon = new ImageIcon("logo.png");
		frame.setIconImage(icon.getImage());
		
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setBackground(GUIConstants.gray);
		
		JLabel title = new JLabel("Alert", 24, GUIConstants.blue, Font.BOLD);
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title, BorderLayout.NORTH);
		
		JLabel msg = new JLabel(content, 18, GUIConstants.black, Font.BOLD);
		msg.setHorizontalAlignment(JLabel.CENTER);
		panel.add(msg, BorderLayout.CENTER);
		
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(parent);
		frame.setVisible(true);
		
		 
		
	}
}
