package controller;

import javax.swing.*;
import java.util.*;

import dao.user.*;
import exception.user.*;
import dao.university.*;

import gui.WindowInterface;
import gui.main.MainWindow;

public class MainController {
	
	private AppController appController;
	private WindowInterface mainWindow;
	private UniversityDAOInterface universityDAO = new UniversityDAOPostgre();
	private UserDAOInterface userDAO = new UserDAOPostgre();

    public MainController() {
        mainWindow = new MainWindow(this);
        appController = new AppController(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainController();
            }
        });
    }
    
    // TODO metodo per passare all'altro controller quando l'utente accede
    
    public void switchTo(String panel) {
    	mainWindow.switchTo(panel);
    }

    public void userValidation(String username, char[] password) throws InvalidUserException {
    	throw new InvalidPasswordException("(Login) Test!");
    }

    public void userValidation(String name, String surname, String username, char[] password, String university) throws InvalidUserException {
    	throw new InvalidUsernameException("(SignUp) Test!");
    }

    // TODO Dovrebbe ritornare un vettore di university
	public Vector<String> getUniversityList() {
		return universityDAO.getNamesList();
	}

}
