package View;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JFrame;

import model.Database;
import model.User;

@SuppressWarnings("serial")
public class SideButton extends JPanel {

	public SideButton(String text, String action, User user, Database database) {
		super(new FlowLayout(FlowLayout.LEFT, 10, 10));
		setMaximumSize(new Dimension(182, 50));
		setBackground(GUIConstants.white);
		setCursor(new Cursor(Cursor.HAND_CURSOR));

		add(new View.JLabel(text, 17, GUIConstants.textAreaHint, Font.BOLD));

		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(GUIConstants.white);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(GUIConstants.hover);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (action.toLowerCase()) {
					case "logout":
						JFrame currentFrame = (JFrame) getTopLevelAncestor();
						if (currentFrame != null) {
							currentFrame.dispose();
						}
						new Login(database);
						break;
					default:
						System.out.println("Unhandled action: " + action);
						break;
				}
			}
		});
	}
}
