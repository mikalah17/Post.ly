package View;

public class JFrame extends javax.swing.JFrame {
    
    public JFrame() {
        super("Post.ly");
        getContentPane().setBackground(GUIConstants.gray);
        setSize (900, 625);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
    
}