package controller;

import dto.UserDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;
import dto.CategoriaDTO;
import dto.ProdottoDTO;

import java.sql.SQLException;
import java.util.ArrayList;

import daoNew.annuncio.*;
import daoNew.product.*;
import daoNew.proposta.*;
import daoNew.category.*;
import gui.WindowInterface;
import gui.app.AppWindow;

public class AppController {
	
	private UserDTO loggedUser;
	
	private AnnuncioDAOInterface AnnunciDAO = new AnnuncioDAOPostgre();
	private ProductDAOInterface ProdottiDAO = new ProductDAOPostgre();
	private PropostaDAOInterface ProposteDAO = new PropostaDAOPostgre();
	private CategoryDAOInterface CategoriaDAO = new CategoryDAOPostgre();
	
	private WindowInterface appWindow;
	
	public AppController(UserDTO loggedUser) {		
		this.loggedUser = loggedUser;
		
		appWindow = new AppWindow(this);		
	}

	public void switchTo(String panel) {
		appWindow.switchTo(panel);
	}
	
	public UserDTO getLoggedUser() {
		return loggedUser;
	}
	
	public ArrayList<CategoriaDTO> getCategorie() throws SQLException {
		return CategoriaDAO.getAllCategories();
	}
	
	public int getNumeroProposte() throws SQLException {
		return ProposteDAO.getNumeroProposte(loggedUser);
	}
	
	public ArrayList<AnnuncioRegaloDTO> getAnnunciRegaloRecenti() throws SQLException {
		return AnnunciDAO.getAnnunciRegaloRecenti(loggedUser);
	}
	
	public ArrayList<AnnuncioVenditaDTO> getAnnunciVenditaRecenti() throws SQLException {
		return AnnunciDAO.getAnnunciVenditaRecenti(loggedUser);
	}
	
	public ArrayList<AnnuncioScambioDTO> getAnnunciScambioRecenti() throws SQLException {
		return AnnunciDAO.getAnnunciScambioRecenti(loggedUser);
	}
	
	public ArrayList<PropostaRegaloDTO> getProposteRegalo() throws SQLException {
		return ProposteDAO.getProposteRegalo(loggedUser);
	}
	
	public ArrayList<PropostaVenditaDTO> getProposteVendita() throws SQLException {
		return ProposteDAO.getProposteVendita(loggedUser);
	}
	
	public ArrayList<PropostaScambioDTO> getProposteScambio() throws SQLException {
		return ProposteDAO.getProposteScambio(loggedUser);
	}
	
	public ArrayList<ProdottoDTO> getProdottiUtente() throws SQLException {
		return ProdottiDAO.getAllUserProduct(loggedUser);
	}
	
	/* 
	 * TODO Oltre tutti i metodi necessari, ne devo aggiungere uno per il refersh dei dati
	 * questo perché ogni volta che un prodotto viene modificato devo poter ripercutoere gli aggiornamenti in gui
	 * quindi per i vari pannelli che contengono prodotti, offerte, annunci e statistiche vado ad aggiornarli
	 */
	
}
