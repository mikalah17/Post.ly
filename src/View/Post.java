package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Post extends JPanel {
	
	public Post (model.Post post) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(GUIConstants.white);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 25));
		
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(null);
		
		JLabel author = new JLabel(post.getUser().getusername(), 20, GUIConstants.post, Font.BOLD);
		header.add(author, BorderLayout.WEST);
		
		JLabel date = new JLabel(post.getDateToString(), 15, GUIConstants.post, Font.PLAIN);
		header.add(date, BorderLayout.EAST);
		
		add(header);
		add(Box.createVerticalStrut(7));
		
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEADING));
		center.setBackground(null);
		JTextArea content = new JTextArea(post.getContent(), 18, GUIConstants.post, Font.PLAIN);
		
		center.add(content);
		add(center);
		add(Box.createVerticalStrut(7));
		
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setBackground(null);
		
		int height = (int) (155+content.getPreferredSize().getHeight());
		
		Dimension dimension = new Dimension(500, height);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);
		
		
	}
	

}
