package gui.main;

import java.awt.event.*;
import javax.swing.*;

import controller.Controller;
import exception.user.*;
import gui.maker.GUIMaker;

class SignUpPanel extends GUIMaker {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ");
	
	SignUpPanel(Controller controller) {
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
	
	private void showSuccessMessage(String message) {
		setGreenColor(infoLabel);
		infoLabel.setText(message);
	}
	
	private void signUpValidation(String name, String surname, String username, char[] password, String university) {
		try {
        	controller.userValidation(name, surname, username, password, university);
        	showSuccessMessage("Utente registrato corretamente!");
        } catch (InvalidUserException error) {
        	showErrorMessage(error.getMessage());
        } catch (Exception error) {
        	showErrorMessage("Si è verificato un problema anomalo!");
        }
	}
	   
    private void createComponents() {
    	JLabel logoLabel = getResizedLogo(0.25);
        
    	JLabel nameLabel = createWhiteLabel("Nome");
        JTextField nameField = createWhiteRoundedTextField(20);
        
        JLabel surnameLabel = createWhiteLabel("Cognome");
        JTextField surnameField = createWhiteRoundedTextField(20);
        
        JLabel usernameLabel = createWhiteLabel("Username");
        JTextField usernameField = createWhiteRoundedTextField(20);

        JLabel passwordLabel = createWhiteLabel("Password");
        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        
        JLabel universityLabel = createWhiteLabel("Università");
        JComboBox<String> universityCombo = createWhiteComboBox(controller.getUniversityList());

        JButton signUpButton = createGreenOutlinedButton("Registrati");
        JButton logInButton = createWhiteUnderlineButton("Vuoi effettuare l'accesso?");

        signUpButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	String name = nameField.getText();
            	String surname = surnameField.getText();
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String university = (String) universityCombo.getSelectedItem();
                
                signUpValidation(name, surname, username, password, university);
            }
        });

        logInButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mainWindowSwitchTo("LogIn");
            	cleanInfoLabel();
            }
        });
        
        createCenteredTopDownGroupLayout(panel, logoLabel, nameLabel, nameField, surnameLabel, surnameField,
        		usernameLabel, usernameField, passwordLabel, passwordField, universityLabel, universityCombo,
        		infoLabel, signUpButton, logInButton);
        
        setEqualSizeGroupLayout(panel, nameField, surnameField, usernameField, passwordField, universityCombo);
    }
}
