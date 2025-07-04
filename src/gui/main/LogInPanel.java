package gui.main;

import java.awt.event.*;
import javax.swing.*;

import controller.Controller;
import exception.user.*;
import gui.maker.GUIMaker;

class LogInPanel extends GUIMaker {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ");
	
	LogInPanel(Controller controller) {
		this.controller = controller;
		
		createComponents();
	}
	
	JPanel getPanel() {
		return panel;
	}
	
	private void cleanInfoLabel() {
		infoLabel.setText(" ");
	}
	
	private void showErrorMessage(String message) {
		setRedColor(infoLabel);
		infoLabel.setText(message);
	}
	
	private void logInValidation(String username, char[] password) {
		try {
        	controller.userValidation(username, password);
        } catch (InvalidUserException error) {
        	showErrorMessage(error.getMessage());
        } catch (Exception error) {
        	showErrorMessage("Si è verificato un problema anomalo!");
        }
	}
	
	private void createComponents() {
		JLabel logoLabel = getResizedLogo(0.25);
		
		JLabel usernameLabel = createWhiteLabel("Username");
        JTextField usernameField = createWhiteRoundedTextField(20);
        
        JLabel passwordLabel = createWhiteLabel("Password");
        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        
        JButton logInButton = createGreenOutlinedButton("Accedi");
        JButton signUpButton = createWhiteUnderlineButton("Non sei ancora registrato?");

        logInButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                
                logInValidation(username, password);
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mainWindowSwitchTo("SignUp");
            	cleanInfoLabel();
            }
        });
		
		createCenteredTopDownGroupLayout(panel, logoLabel, usernameLabel, usernameField, passwordLabel, passwordField,
				infoLabel, logInButton, signUpButton);
		
		setEqualSizeGroupLayout(panel, usernameField, passwordField);
	}
}
