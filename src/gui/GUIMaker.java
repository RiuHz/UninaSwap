package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public abstract class GUIMaker {
	
	private final Color WHITE = new Color(0xFFFFFF);
	private final Color BLUE = new Color(0x0F3A5F);
	private final Color YELLOW = new Color(0xFCB520);
	private final Color RED = new Color(0xDF2935);
	private final Color GREEN = new Color(0x31E981);
		
	protected JPanel createBluePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(BLUE);
		
		return panel;
	}
	
    protected JLabel createWhiteLabel(String text, int fontSize) {
    	JLabel label = new JLabel(text);
    	label.setForeground(WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
        
        return label;
    }
    
    protected JTextField createWhiteRoundedTextField(int columnSize) {
    	JTextField field = new JTextField(columnSize);
    	
    	// TODO Create textfield
    		
    	return field;
    }
    
    protected JPasswordField createWhiteRoundedPasswordField(int columnSize) {
    	JPasswordField field = new JPasswordField(columnSize);
    	
    	// TODO Create passwordfield
    	
    	return field;
    }
     
    protected JButton createGreenOutlinedButton(String text) {
    	JButton button = new JButton(text);
    	
    	// TODO Create button
    	
    	return button;
    }
    
    protected JButton createWhiteUnderlineButton(String text) {
    	JButton button = new JButton(text);
    	
    	// TODO Create button
    	
    	return button;
    }
}
