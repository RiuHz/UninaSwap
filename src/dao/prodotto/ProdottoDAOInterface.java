package dao.prodotto;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UtenteDTO;

public interface ProdottoDAOInterface {

	public ArrayList<ProdottoDTO> getProdottiUtente(UtenteDTO utente) throws SQLException;
	
	public ArrayList<ProdottoDTO> getProdottiElegibili(UtenteDTO utente) throws SQLException;
	
	public void creaProdotto(UtenteDTO utente, String nome, byte[] immagine, String descrizione, CategoriaDTO categoria) throws SQLException;
	
}
