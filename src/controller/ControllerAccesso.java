package controller;

import javax.swing.*;

import java.sql.SQLException;
import java.util.*;

import dao.universita.*;
import dao.universita.postgre.UniversitaDAOPostgre;
import dao.utente.*;
import dao.utente.postgre.UtenteDAOPostgre;
import dto.UniversitaDTO;
import dto.UtenteDTO;
import gui.InterfacciaFinestraAccesso;
import gui.main.FinestraAccesso;

public class ControllerAccesso {
		
	private InterfacciaFinestraAccesso finestraLogin;
	
	private final UtenteDAOInterface UtenteDAO = new UtenteDAOPostgre();
	private final UniversitaDAOInterface UniversitaDAO = new UniversitaDAOPostgre();
	
    public ControllerAccesso() {
        finestraLogin = new FinestraAccesso(this);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new ControllerAccesso();
            }
        });
    }
    
    public void mostraPannello(String pannello) {
    	finestraLogin.mostraPannello(pannello);
    }
    
    private void apriApp(UtenteDTO utenteLoggato) {
    	finestraLogin.nascondiFinestra();
    	
    	new ControllerApp(utenteLoggato);
    }
    
    public void logInUtente(String username, char[] password) throws SQLException {
    	UtenteDTO utenteLoggato = UtenteDAO.logInUtente(username, password);

        apriApp(utenteLoggato);
    }

    public void signUpUtente(String nome, String cognome, String username, char[] password, UniversitaDTO universita) throws SQLException {
    	UtenteDAO.creaUtente(nome, cognome, username, password, universita);
    }

	public ArrayList<UniversitaDTO> getListaUniversita() throws SQLException {
		return UniversitaDAO.getListaNomiUniversita();
	}

}
