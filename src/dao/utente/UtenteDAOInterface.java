package dao.utente;

import java.sql.SQLException;

import dto.UniversitaDTO;
import dto.UtenteDTO;

public interface UtenteDAOInterface {

	public void creaUtente(String nome, String cognome, String username, char[] password, UniversitaDTO universita) throws SQLException;
	
	public UtenteDTO logInUtente(String username, char[] password) throws SQLException;
	
}
