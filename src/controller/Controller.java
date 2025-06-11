package controller;

import javax.swing.SwingUtilities;

import java.util.ArrayList;

import dao.user.*;
import dao.university.*;

import gui.main.MainWindow;

public class Controller {
	
	private MainWindow loginFrame;
	private UniversityDAOInterface universityDAO = new UniversityDAOPostgre();
	// private appFrame = null;
	private UserDAOInterface userDAO = new UserDAOPostgre();

    public Controller() {
        loginFrame = new MainWindow(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Controller();
            }
        });
    }

    public void validateLogin(String username, String password) {
    	loginFrame.showInvalidPasswordError();
    }


	public void validateRegistration(String username, String password, String name, String surname, String university) {
		loginFrame.showInvalidUsernameError();
	}

	public ArrayList<String> getUniversityList() {
		return universityDAO.getNamesList();
	}

}
