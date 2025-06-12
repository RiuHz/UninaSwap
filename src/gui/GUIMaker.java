package gui;

import java.awt.*;
import java.net.*;
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
		URL logoURL = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(logoURL);
		
		int width = (int) (logo.getIconWidth() * sizeMultiplier);
		int height = (int) (logo.getIconHeight() * sizeMultiplier);
		
		Image resizedLogo = logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); 
		
		return new JLabel(new ImageIcon(resizedLogo));
	}
	
	protected Image getIconImage() {
		URL iconURL = getClass().getResource("/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		
		Taskbar.getTaskbar().setIconImage(icon.getImage());
		
		return icon.getImage();
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
