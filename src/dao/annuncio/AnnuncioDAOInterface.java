package dao.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioDTO;

public interface AnnuncioDAOInterface {

	public ArrayList<AnnuncioDTO> getAnnunciRecenti(UtenteDTO utente) throws SQLException;
	
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecenti(UtenteDTO utente) throws SQLException;
	
	public ArrayList<AnnuncioDTO> getAnnunciScambioRecenti(UtenteDTO utente) throws SQLException;
	
	public ArrayList<AnnuncioDTO> getAnnunciVenditaRecenti(UtenteDTO utente) throws SQLException;
	
	public ArrayList<AnnuncioDTO> getAnnunciRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException;
	
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException;

	public ArrayList<AnnuncioDTO> getAnnunciScambioRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException;

	public ArrayList<AnnuncioDTO> getAnnunciVenditaRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException;

	public void creaAnnuncioRegalo(ProdottoDTO prodotto, String consegna) throws SQLException;
	
	public void creaAnnuncioVendita(ProdottoDTO prodotto, String consegna, double prezzo) throws SQLException;
	
	public void creaAnnuncioScambio(ProdottoDTO prodotto, String consegna) throws SQLException;
	
}
