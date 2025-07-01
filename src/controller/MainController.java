package controller;

import javax.swing.*;

import DTO.UserDTOPostgre;

import java.util.*;

import entity.UserEntity;

import dao.user.*;
import exception.user.*;
import dao.university.*;

import gui.WindowInterface;
import gui.main.MainWindow;

public class MainController {

	private AppController appController;
	private WindowInterface mainWindow;
	private UniversityDAOInterface universityDAO = new UniversityDAOPostgre();


	UserEntity utente;

	UserDTOPostgre userDTO;

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

    // TODO metodo per passare all'altro controller quando l'utente accede

    public void switchTo(String panel) {
    	mainWindow.switchTo(panel);
    }

    public void userValidation(String username, char[] password) throws InvalidUserException {
    	utente= new UserEntity(username,password);
    	userDTO=new UserDTOPostgre(utente);
    	userDTO.checkLogin();
        appController = new AppController(this);//io qua gli passerei il nome utente
    }

    public void userValidation(String name, String surname, String username, char[] password, String university) throws InvalidUserException {
    	utente= new UserEntity(name,surname,username,password,university);
    	userDTO=new UserDTOPostgre(utente);
    	userDTO.verifyAll();
    	userDTO.registerUser();
    }

    // TODO Dovrebbe ritornare un vettore di university
	public Vector<String> getUniversityList() {
		return universityDAO.getNamesList();
	}

}
