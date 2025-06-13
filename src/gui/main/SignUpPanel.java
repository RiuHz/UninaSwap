package gui.main;

import java.awt.event.*;
import javax.swing.*;

import controller.Controller;
import exception.user.*;
import gui.GUIMaker;

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
        } catch (InvalidUsernameException error) {
        	showErrorMessage(error.getMessage());
        } catch (InvalidPasswordException error) {
        	showErrorMessage(error.getMessage());
        } catch (Exception error) {
        	showErrorMessage("Si è verificato un problema anomalo!");
        }
	}
	   
    private void createComponents() {
    	JLabel logoLabel = getResizedLogo(0.25);
        
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
                char[] password = passwordField.getPassword();
                String university = (String) universityCombo.getSelectedItem();
                
                signUpValidation(name, surname, username, password, university);
            }
        });

        logInButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	controller.mainWindowSwitchTo("LogIn");
            	cleanInfoLabel();
            }
        });
        
        createCenteredTopDownGroupLayout(panel, logoLabel, nameField, surnameField, usernameField, passwordField, universityCombo, infoLabel, signUpButton, logInButton);
        
        setEqualSizeGroupLayout(panel, nameField, surnameField, usernameField, passwordField, universityCombo);
    }
}
