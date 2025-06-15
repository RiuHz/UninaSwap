package gui.maker;

import java.awt.*;
import java.net.*;
import javax.swing.*;

import java.util.*;

public abstract class GUIMaker extends GUIClasses {
	
	protected void setRedColor(JLabel label) {
		label.setForeground(RED);
	}
	
	protected void setGreenColor(JLabel label) {
		label.setForeground(GREEN);
	}
	
    protected JButton createGreenOutlinedButton(String text) {
    	return new OutlinedButton(text, GREEN, WHITE);
    }
    
    protected JButton createWhiteUnderlineButton(String text) {
    	return new UnderlineButton(text, WHITE);
    }
    
    protected JTextField createWhiteRoundedTextField(int columnSize) {
    	return new RoundedTextField(columnSize, WHITE);
    }
    
    protected JPasswordField createWhiteRoundedPasswordField(int columnSize) {
    	return new RoundedPasswordField(columnSize, WHITE);
    }
    
    protected <Type> JComboBox<Type> createWhiteComboBox(Vector<Type> elements) {
    	JComboBox<Type> comboBox = new JComboBox<Type>(elements);
    	comboBox.setFont(FONT);
    	comboBox.setForeground(BLACK);
    	comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	
    	return comboBox;
    }
    
    protected JLabel createWhiteLabel(String text) {
    	JLabel label = new JLabel(text);
    	label.setFont(FONT);
    	label.setForeground(WHITE);
        
        return label;
    }
	
	protected JPanel createBluePanel() {
		JPanel panel = new JPanel();
		panel.setBackground(BLUE);

		return panel;
	}
	
	protected Image getIconImage() {
		URL iconURL = getClass().getResource("/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		
		return icon.getImage();
	}
	
	protected JLabel getResizedLogo(double sizeMultiplier) {
		URL logoURL = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(logoURL);
		
		int width = (int) (logo.getIconWidth() * sizeMultiplier);
		int height = (int) (logo.getIconHeight() * sizeMultiplier);
		
		Image resizedLogo = logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); 
		
		return new JLabel(new ImageIcon(resizedLogo));
	}
    
    protected void setEqualSizeGroupLayout(JPanel panel, JComponent ...components) {
    	GroupLayout layout = (GroupLayout) panel.getLayout();
    	
        layout.linkSize(components);
    }
    
    protected void createCenteredTopDownGroupLayout(JPanel panel, JComponent ...components) {
        GroupLayout layout = new GroupLayout(panel);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        verticalGroup.addGap(0, 0, Integer.MAX_VALUE);
        horizontalGroup.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent component : components) {
            verticalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            horizontalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        
        verticalGroup.addGap(0, 0, Integer.MAX_VALUE);

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
        
        panel.setLayout(layout);
    }
    
}
