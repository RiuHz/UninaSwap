package dao.proposta;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;

public interface PropostaDAOInterface {

	public int getNumeroProposteRicevuteInAttesa(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteRegaloInviate(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteRegaloAccettate(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteVenditaInviate(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteVenditaAccettate(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteScambioInviate(UtenteDTO utente) throws SQLException;
	
	public int getNumeroProposteScambioAccettate(UtenteDTO utente) throws SQLException;
	
	public double getPrezzoMinimoAccettato(UtenteDTO utente) throws SQLException;
	
	public double getPrezzoMediaAccettato(UtenteDTO utente) throws SQLException;
	
	public double getPrezzoMassimoAccettato(UtenteDTO utente) throws SQLException;
	
	public ArrayList<PropostaDTO> getProposteRicevute(UtenteDTO utente) throws SQLException;
	
	public ArrayList<PropostaDTO> getProposteInviate(UtenteDTO utente) throws SQLException;
	
	public void creaPropostaRegalo(UtenteDTO utente, AnnuncioRegaloDTO annuncio, String messaggio) throws SQLException;
	
	public void accettaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException;
	
	public void rifiutaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException;
	
	public void modificaPropostaRegalo(PropostaRegaloDTO proposta, String messaggio) throws SQLException;
	
	public void ritiraPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException;
	
	public void creaPropostaVendita(UtenteDTO utente, AnnuncioVenditaDTO annuncio, double prezzo) throws SQLException;
	
	public void accettaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException;
	
	public void rifiutaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException;
	
	public void modificaPropostaVendita(PropostaVenditaDTO proposta, double prezzo) throws SQLException;
	
	public void ritiraPropostaVendita(PropostaVenditaDTO proposta) throws SQLException;
	
	public void creaPropostaScambio(UtenteDTO utente, AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> prodotti) throws SQLException;
	
	public void accettaPropostaScambio(PropostaScambioDTO proposta) throws SQLException;
	
	public void rifiutaPropostaScambio(PropostaScambioDTO proposta) throws SQLException;
	
	public void modificaPropostaScambio(PropostaScambioDTO proposta, ArrayList<ProdottoDTO> prodotti) throws SQLException;
	
	public void ritiraPropostaScambio(PropostaScambioDTO proposta) throws SQLException;

}
