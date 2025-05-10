package View;

public class JFrame extends javax.swing.JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JFrame() {
        super("Post.ly");
        getContentPane().setBackground(GUIConstants.gray);
        setSize (900, 625);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
    
}