package controller;

import javax.swing.*;
import java.util.*;

import dao.user.*;
import exception.user.*;
import dao.university.*;

import gui.main.MainWindow;

public class Controller {
	
	private MainWindow mainWindow;
	private UniversityDAOInterface universityDAO = new UniversityDAOPostgre();
	// private appFrame = null;
	private UserDAOInterface userDAO = new UserDAOPostgre();

    public Controller() {
        mainWindow = new MainWindow(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Controller();
            }
        });
    }
    
    public void mainWindowSwitchTo(String panel) {
    	mainWindow.switchTo(panel);
    }

    public void userValidation(String username, char[] password) throws InvaldUserException {
    	throw new InvalidPasswordException("(Password) Test!");
    }
    
    public void userValidation(String name, String surname, String username, char[] password, String university) throws InvaldUserException {
    	throw new InvalidUsernameException("(Username) Test!");
    }

	public void validateRegistration(String username, String password, String name, String surname, String university) {
		
	}

	public Vector<String> getUniversityList() {
		return universityDAO.getNamesList();
	}

}
