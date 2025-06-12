package gui.main;

import java.awt.event.*;
import javax.swing.*;

import controller.Controller;
import gui.GUIMaker;
import gui.GUIComponent;

class SignUpPanel extends GUIMaker implements GUIComponent {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ", 14);
	
	SignUpPanel(Controller controller) {
		this.controller = controller;
		
		createComponents();
		        
        show();
	}
	
	JPanel getPanel() {
		return panel;
	}
	
	public void show() {
		panel.setVisible(true);
	}
	
	public void hide() {
		panel.setVisible(false);
	}
	   
    private void createComponents() {
    	JLabel logoLabel = getResizedLogo(0.2);
        
        JTextField nameField = createWhiteRoundedTextField(20);
        JTextField surnameField = createWhiteRoundedTextField(20);
        JTextField usernameField = createWhiteRoundedTextField(20);

        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        JComboBox<String> universityCombo = createWhiteRoundedComboBox(controller.getUniversityList());

        JButton signUpButton = createGreenOutlinedButton("Registrati");
        JButton logInButton = createWhiteUnderlineButton("Accedi");

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	String name = nameField.getText();
            	String surname = surnameField.getText();
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword(); // TODO Deprecato?
                String university = (String) universityCombo.getSelectedItem();
                // TODO Richiama un metodo per la validazione della registrazione
                System.out.println("(Pannello di Registrazione) Premuto il bottone di registrazione!");
            }
        });

        logInButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	// TODO richiama un metodo per il cambio di pannello
            	System.out.println("(Pannello di Registrazione) Premuto il bottone di accesso!");
            }
        });
        
        createLayout(panel, logoLabel, nameField, surnameField, usernameField, passwordField, universityCombo, infoLabel, signUpButton, logInButton);
    }
    
    private void createLayout(JPanel panel, JComponent ... components) {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        for (JComponent component : components) {
            verticalGroup.addComponent(component);
            horizontalGroup.addComponent(component);
        }

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
    }
}
