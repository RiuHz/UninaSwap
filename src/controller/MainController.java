package controller;

import javax.swing.*;

import java.sql.SQLException;
import java.util.*;

import daoNew.user.*;
import daoNew.university.*;
import dto.UserDTO;
import gui.WindowInterface;
import gui.main.MainWindow;

public class MainController {
	
	private WindowInterface mainWindow;
	
	private UserDAOInterface UserDAO = new UserDAOPostgre();
	private UniversityDAOInterface UniversitaDAO = new UniversityDAOPostgre();
	
    public MainController() {
        mainWindow = new MainWindow(this);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MainController();
            }
        });
    }

    public void switchTo(String panel) {
    	mainWindow.switchTo(panel);
    }
    
    private void openApp(UserDTO loggedUser) {
    	mainWindow.hideWindow();
    	
    	new AppController(loggedUser);
    }
    
    public void logInUser(String username, char[] password) throws SQLException {
    	UserDTO loggedUser = UserDAO.logInUser(username, password);

        openApp(loggedUser);
    }

    public void signUpUser(String name, String surname, String username, char[] password, String university) throws SQLException {
    	UserDAO.create(name, surname, username, password, university);
    }

	public Vector<String> getUniversityList() throws SQLException {
		return UniversitaDAO.getNamesList();
	}

}
