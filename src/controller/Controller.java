package controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dao.UniversityDAOPostgre;

import java.util.ArrayList;

import entities.user.UserDAO;
import gui.MainWindow;

public class Controller {
	private MainWindow loginFrame;
	private UniversityDAOPostgre uniDao;
	// private appFrame = null;
	private UserDAO user = new UserDAO();

    public Controller() {
        uniDao=new UniversityDAOPostgre();
        loginFrame = new MainWindow(this);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
            controller.loginFrame.setVisible(true);
        });
    }

    public void validateLogin(String username, String password) {
        if (user.validLogin(username, password)) {
            // appFrame.show();
        } else if (user.invalidUsername()) {
            loginFrame.showInvalidUsernameError();
        } else {
            loginFrame.showInvalidPasswordError();
        }
    }


	public void validateRegistration(String username, String password, String name, String surname, String university) {
		if (user.validRegistration(username, password, name, surname, university))
			loginFrame.showSuccessRegistration();
		else
			loginFrame.showExistingUsernameError();
	}

	public ArrayList<String> getUniversityList() {
		return uniDao.getNamesList();
	}

}
