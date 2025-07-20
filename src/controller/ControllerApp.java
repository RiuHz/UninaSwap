package controller;

import dto.UtenteDTO;
import dto.annunci.AnnuncioDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;
import dto.CategoriaDTO;
import dto.ProdottoDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import dao.annuncio.AnnuncioDAOInterface;
import dao.annuncio.postgre.AnnuncioDAOPostgre;
import dao.categoria.CategoriaDAOInterface;
import dao.categoria.postgre.CategoriaDAOPostgre;
import dao.prodotto.ProdottoDAOInterface;
import dao.prodotto.postgre.ProdottoDAOPostgre;
import dao.proposta.PropostaDAOInterface;
import dao.proposta.postgre.PropostaDAOPostgre;
import gui.InterfacciaFinestraApp;
import gui.app.FinestraApp;

public class ControllerApp {
	
	private UtenteDTO utenteLoggato;
	
	private AnnuncioDAOInterface AnnuncioDAO = new AnnuncioDAOPostgre();
	private ProdottoDAOInterface ProdottoDAO = new ProdottoDAOPostgre();
	private PropostaDAOInterface PropostaDAO = new PropostaDAOPostgre();
	private CategoriaDAOInterface CategoriaDAO = new CategoriaDAOPostgre();

	private InterfacciaFinestraApp finestraApp;
	
	public ControllerApp(UtenteDTO utenteLoggato) {		
		this.utenteLoggato = utenteLoggato;
		
		finestraApp = new FinestraApp(this);		
	}

	public void mostra(String panello) {
		finestraApp.mostraPannello(panello);
	}
	
	public ArrayList<CategoriaDTO> getListaCategorie() throws SQLException {
		return CategoriaDAO.getListaCategorie();
	}
	
	public ArrayList<AnnuncioDTO> getAnnunciRecenti() throws SQLException {
		return AnnuncioDAO.getAnnunciRecenti(utenteLoggato);
	}
	
	public ArrayList<PropostaDTO> getProposteRicevute() throws SQLException {
		return PropostaDAO.getProposteRicevute(utenteLoggato);
	}
	
	public ArrayList<PropostaDTO> getProposteInviate() throws SQLException {
		return PropostaDAO.getProposteInviate(utenteLoggato);
	}
	
	public void creaProdotto(String nome, byte[] immagine, String descrizione, CategoriaDTO categoria) throws SQLException {
		ProdottoDAO.creaProdotto(utenteLoggato, nome, immagine, descrizione, categoria);
		finestraApp.aggiornaInventario(getProdottiUtente());
	}
	
	public ArrayList<ProdottoDTO> getProdottiUtente() throws SQLException {
		return ProdottoDAO.getProdottiUtente(utenteLoggato);
	}
	
	public ArrayList<ProdottoDTO> getProdottiElegibiliPerAnnuncio() throws SQLException {
		return ProdottoDAO.getProdottiElegibili(utenteLoggato);
	}
	
	public ArrayList<ProdottoDTO> getProdottiElegibiliPerScambio() throws SQLException {
		return ProdottoDAO.getProdottiElegibili(utenteLoggato);
	}
	
	public int getNumeroProposteRicevuteInAttesa() throws SQLException {
		return PropostaDAO.getNumeroProposteRicevuteInAttesa(utenteLoggato);
	}
	
	public int getNumeroProposteRegaloInviate() throws SQLException {
		return PropostaDAO.getNumeroProposteRegaloInviate(utenteLoggato);
	}
	
	public int getNumeroProposteRegaloAccettate() throws SQLException {
		return PropostaDAO.getNumeroProposteRegaloAccettate(utenteLoggato);
	}
	
	public int getNumeroProposteVenditaInviate() throws SQLException {
		return PropostaDAO.getNumeroProposteVenditaInviate(utenteLoggato);
	}
	
	public int getNumeroProposteVenditaAccettate() throws SQLException {
		return PropostaDAO.getNumeroProposteVenditaAccettate(utenteLoggato);
	}
	
	public int getNumeroProposteScambioInviate() throws SQLException {
		return PropostaDAO.getNumeroProposteScambioInviate(utenteLoggato);
	}
	
	public int getNumeroProposteScambioAccettate() throws SQLException {
		return PropostaDAO.getNumeroProposteScambioAccettate(utenteLoggato);
	}
	
	public double getMinimoAccettatoPropostaVendita() throws SQLException {
		return PropostaDAO.getPrezzoMinimoAccettato(utenteLoggato);
	}
	
	public double getMediaAccettatoPropostaVendita() throws SQLException {
		return PropostaDAO.getPrezzoMediaAccettato(utenteLoggato);
	}
	
	public double getMassimoAccettatoPropostaVendita() throws SQLException {
		return PropostaDAO.getPrezzoMassimoAccettato(utenteLoggato);
	}
	
	public void creaPropostaRegalo(AnnuncioRegaloDTO annuncio, String messaggio) throws SQLException {
		PropostaDAO.creaPropostaRegalo(utenteLoggato, annuncio, messaggio);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void accettaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaDAO.accettaPropostaRegalo(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void rifiutaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaDAO.rifiutaPropostaRegalo(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void modificaPropostaRegalo(PropostaRegaloDTO proposta, String messaggio) throws SQLException {
		PropostaDAO.modificaPropostaRegalo(proposta, messaggio);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
	}
	
	public void ritiraPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaDAO.ritiraPropostaRegalo(proposta);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void creaPropostaVendita(AnnuncioVenditaDTO annuncio, double prezzo) throws SQLException {
		PropostaDAO.creaPropostaVendita(utenteLoggato, annuncio, prezzo);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void accettaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaDAO.accettaPropostaVendita(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void rifiutaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaDAO.rifiutaPropostaVendita(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void modificaPropostaVendita(PropostaVenditaDTO proposta, double prezzo) throws SQLException {
		PropostaDAO.modificaPropostaVendita(proposta, prezzo);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
	}
	
	public void ritiraPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaDAO.ritiraPropostaVendita(proposta);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void creaPropostaScambio(AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		PropostaDAO.creaPropostaScambio(utenteLoggato, annuncio, prodotti);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void accettaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaDAO.accettaPropostaScambio(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void rifiutaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaDAO.rifiutaPropostaScambio(proposta);
		finestraApp.aggiornaProposteRicevute(getProposteRicevute());
	}
	
	public void modificaPropostaScambio(PropostaScambioDTO proposta, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		PropostaDAO.modificaPropostaScambio(proposta, prodotti);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
	}
	
	public void ritiraPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaDAO.ritiraPropostaScambio(proposta);
		finestraApp.aggiornaProposteInviate(getProposteInviate());
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void creaAnnuncioRegalo(ProdottoDTO prodotto, String consegna) throws SQLException {
		AnnuncioDAO.creaAnnuncioRegalo(prodotto, consegna);
	}
	
	public void creaAnnuncioVendita(ProdottoDTO prodotto, String consegna, double prezzo) throws SQLException {
		AnnuncioDAO.creaAnnuncioVendita(prodotto, consegna, prezzo);
	}
	
	public void creaAnnuncioScambio(ProdottoDTO prodotto, String consegna) throws SQLException {
		AnnuncioDAO.creaAnnuncioScambio(prodotto, consegna);
	}
	
	public void aggiornaAnnunci() throws SQLException {
		finestraApp.aggiornaAnnunci(getAnnunciRecenti());
	}
	
	public void aggiornaAnnunciPerCategoria(ArrayList<Integer> categorie) throws SQLException {
		finestraApp.aggiornaAnnunci(AnnuncioDAO.getAnnunciRecentiPerCategoria(utenteLoggato, categorie));
	}
	
	public void aggiornaAnnunciPerTipologia(ArrayList<String> tipologie) throws SQLException {
		ArrayList<AnnuncioDTO> listaAnnunci = new ArrayList<AnnuncioDTO>();
		
		for (String tipologia : tipologie)
			switch (tipologia) {
				case "Regalo" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciRegaloRecenti(utenteLoggato));
				case "Scambio" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciScambioRecenti(utenteLoggato));
				case "Vendita" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciVenditaRecenti(utenteLoggato));
			}
		
		listaAnnunci.sort(Comparator.comparing(AnnuncioDTO::getData));
		
		finestraApp.aggiornaAnnunci(listaAnnunci);
	}
	
	public void aggiornaAnnunci(ArrayList<String> tipologie, ArrayList<Integer> categorie) throws SQLException {
		ArrayList<AnnuncioDTO> listaAnnunci = new ArrayList<AnnuncioDTO>();
		
		for (String tipologia : tipologie)
			switch (tipologia) {
			case "Regalo" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciRegaloRecentiPerCategoria(utenteLoggato, categorie));
			case "Scambio" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciScambioRecentiPerCategoria(utenteLoggato, categorie));
			case "Vendita" -> listaAnnunci.addAll(AnnuncioDAO.getAnnunciVenditaRecentiPerCategoria(utenteLoggato, categorie));
			}
		
		listaAnnunci.sort(Comparator.comparing(AnnuncioDTO::getData));
		
		finestraApp.aggiornaAnnunci(listaAnnunci);
	}

}
