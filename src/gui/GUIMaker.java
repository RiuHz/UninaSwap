package gui;

import java.awt.*;
import javax.swing.*;
import java.util.*;

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
	
	protected JLabel getResizedLogo(double sizeMultiplier) {
		ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/src/img/logo.png");
		
		int width = (int) (logo.getIconWidth() * sizeMultiplier);
		int height = (int) (logo.getIconHeight() * sizeMultiplier);
		
		Image resizedLogo = logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); 
		
		return new JLabel(new ImageIcon(resizedLogo));
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
    
    protected <Type> JComboBox<Type> createWhiteRoundedComboBox(Vector<Type> elements) {
    	JComboBox<Type> box = new JComboBox<Type>(elements);
    	
    	return box;
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
