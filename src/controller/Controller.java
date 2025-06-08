package controller;

import javax.swing.JFrame;
import java.util.ArrayList;

import entities.university.UniversityDAO;
import entities.user.UserDAO;

public class Controller {
	private MainWindow loginFrame = new MainWindow(this);
	// private appFrame = null;
	private UserDAO user = new UserDAO();

	public static void main(String[] args) {
		loginFrame.show();
	}

	public void validateLogin(String username, String password) {
		if (user.validLogin(username, password))
			// appFrame.show();
		else if (user.invalidUsername());
			loginFrame.showInvalidUsernameError();
		else
			loginFrame.showInvalidPasswordError();
	}
	
	public void validateRegistration(String username, String password, String name, String surname, String university) {
		if (user.validRegistration(username, password, name, surname, university))
			loginFrame.showSuccessRegistration();
		else
			loginFrame.showExistingUsernameError();
	}
	
	public ArrayList<String> getUniversityList() {
		return UniversityDAO.getNamesList();
	}
	
}
