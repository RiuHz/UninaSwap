package gui.main;

import java.awt.event.*;
import javax.swing.*;

import controller.Controller;

import gui.GUIComponent;
import gui.GUIMaker;

class LogInPanel extends GUIMaker implements GUIComponent {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ", 14);
	
	LogInPanel(Controller controller) {
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
		
        JTextField usernameField = createWhiteRoundedTextField(20);
        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        
        JButton logInButton = createGreenOutlinedButton("Accedi");
        JButton signUpButton = createWhiteUnderlineButton("Registrati");

        logInButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // TODO Richiama un metodo per la validazione dell'accesso
                System.out.println("(Pannello di Accesso) Premuto il bottone di accesso!");
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	// TODO richiama un metodo per il cambio di pannello
            	System.out.println("(Pannello di Accesso) Premuto il bottone di registrazione!");
            }
        });
		
		createLayout(panel, logoLabel, usernameField, passwordField, infoLabel, logInButton, signUpButton);
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
