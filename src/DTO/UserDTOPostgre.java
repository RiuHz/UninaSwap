package DTO;

import dao.user.UserDAOInterface;
import dao.user.UserDAOPostgre;
import entity.UserEntity;
import exception.user.InvalidUserException;

public class UserDTOPostgre {
	UserDAOInterface userDAO;
    UserEntity user;

    public UserDTOPostgre(UserEntity user) {
        this.user = user;
    }

    public void verifyAll() throws InvalidUserException{
    	this.verifyName();
        this.verifySurname();
    	this.verifyUsername();
    	this.verifyPassword();
    }

    public void checkLogin() throws InvalidUserException {
        userDAO = new UserDAOPostgre(user);
        boolean trovato = userDAO.checkCredentials(user);
        if (!trovato) {
            throw new InvalidUserException("Username o password errati.");
        }
    }

    public void registerUser() throws InvalidUserException {
        userDAO = new UserDAOPostgre(user);
        boolean success = userDAO.insertUser(user);
        if (!success) {
            throw new InvalidUserException("Registrazione fallita: utente già esistente");
        }
    }

    private void verifyName() throws InvalidUserException {
        String name = user.getName();

        if (name == null || name.isEmpty()) {
            throw new InvalidUserException("Il nome non può essere nullo o vuoto.");
        }

        if (name.length() < 2 || name.length() > 15) {
            throw new InvalidUserException("La lunghezza del nome deve essere tra 2 e 30 caratteri.");
        }

        if (!name.matches("^[A-Z ][a-zA-Z ]*$")) {
            throw new InvalidUserException("Il nome deve contenere solo lettere, senza simboli, e iniziare con una maiuscola.");
        }
    }

    private void verifySurname() throws InvalidUserException {
        String surname = user.getSurname();

        if (surname == null || surname.isEmpty()) {
            throw new InvalidUserException("Il cognome non può essere nullo o vuoto.");
        }

        if (surname.length() < 2 || surname.length() > 15) {
            throw new InvalidUserException("La lunghezza del cognome deve essere tra 2 e 30 caratteri.");
        }

        if (!surname.matches("^[A-Z ][a-zA-Z ]*$")) {
            throw new InvalidUserException("Il cognome deve contenere solo lettere, senza simboli, e iniziare con una maiuscola.");
        }
    }


    private void verifyUsername() throws InvalidUserException {
        String username = user.getUserName();

        if (username == null || username.isEmpty()) {
            throw new InvalidUserException("Username non può essere nullo o vuoto.");
        }

        if (username.length() < 3 || username.length() > 15) {
            throw new InvalidUserException("La lunghezza dello username deve essere tra 3 e 15 caratteri.");
        }

        if (!username.matches("^[a-zA-Z_]+$")) {
            throw new InvalidUserException("Lo username può contenere solo lettere e trattini bassi.");
        }
    }

    public void verifyPassword() throws InvalidUserException {
        char[] passwordChars = user.getPassword();

        if (passwordChars == null || passwordChars.length == 0) {
            throw new InvalidUserException("Password non può essere nulla o vuota.");
        }

        String password = new String(passwordChars);

        if (password.length() < 8 || password.length() > 15) {
            throw new InvalidUserException("La password deve essere lunga tra 8 e 15 caratteri.");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidUserException("La password deve contenere almeno una lettera maiuscola.");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new InvalidUserException("La password deve contenere almeno un carattere speciale.");
        }
    }

}
