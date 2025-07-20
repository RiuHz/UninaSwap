package dao.proposta.postgre;

import java.sql.*;
import java.util.ArrayList;

import dao.DatabaseManager;
import dao.proposta.PropostaDAOInterface;
import dto.ProdottoDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;

public class PropostaDAOPostgre extends DatabaseManager implements PropostaDAOInterface {
	
	private PropostaRegaloDAOPostgre PropostaRegaloDAO = new PropostaRegaloDAOPostgre();
	private PropostaVenditaDAOPostgre PropostaVenditaDAO = new PropostaVenditaDAOPostgre();
	private PropostaScambioDAOPostgre PropostaScambioDAO = new PropostaScambioDAOPostgre();

	@Override
	public int getNumeroProposteRicevuteInAttesa(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM ( 
						(
							SELECT Proposta_Vendita.Username
							FROM Proposta_Vendita
							JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
							JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
							WHERE Proposta_Vendita.Username <> ? AND Prodotto.Username = ? AND Proposta_Vendita.Stato = 'In Attesa'
						)
					UNION ALL
						(
						SELECT Proposta_Regalo.Username
						FROM Proposta_Regalo
						JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
						JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
						WHERE Proposta_Regalo.Username <> ? AND Prodotto.Username = ? AND Proposta_Regalo.Stato = 'In Attesa'
						)
					UNION ALL
					(
						SELECT Proposta_Scambio.Username
						FROM Proposta_Scambio
						JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scambio = Annuncio_Scambio.ID_Ann_Scambio
						JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
						WHERE Proposta_Scambio.Username <> ? AND Prodotto.Username = ? AND Proposta_Scambio.Stato = 'In Attesa'
					)
				)
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		statement.setString(3, utente.getUsername());
		statement.setString(4, utente.getUsername());
		statement.setString(5, utente.getUsername());
		statement.setString(6, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		return result.getInt("Count");
	}

	@Override
	public int getNumeroProposteRegaloInviate(UtenteDTO utente) throws SQLException {
		return PropostaRegaloDAO.getNumeroProposteRegaloInviate(utente);
	}

	@Override
	public int getNumeroProposteRegaloAccettate(UtenteDTO utente) throws SQLException {
		return PropostaRegaloDAO.getNumeroProposteRegaloAccettate(utente);
	}

	@Override
	public int getNumeroProposteVenditaInviate(UtenteDTO utente) throws SQLException {
		return PropostaVenditaDAO.getNumeroProposteVenditaInviate(utente);
	}

	@Override
	public int getNumeroProposteVenditaAccettate(UtenteDTO utente) throws SQLException {
		return PropostaVenditaDAO.getNumeroProposteVenditaAccettate(utente);
	}

	@Override
	public int getNumeroProposteScambioInviate(UtenteDTO utente) throws SQLException {
		return PropostaScambioDAO.getNumeroProposteScambioInviate(utente);
	}

	@Override
	public int getNumeroProposteScambioAccettate(UtenteDTO utente) throws SQLException {
		return PropostaScambioDAO.getNumeroProposteScambioAccettate(utente);
	}

	@Override
	public double getPrezzoMinimoAccettato(UtenteDTO utente) throws SQLException {
		return PropostaVenditaDAO.getPrezzoMinimoAccettato(utente);
	}

	@Override
	public double getPrezzoMediaAccettato(UtenteDTO utente) throws SQLException {
		return PropostaVenditaDAO.getPrezzoMediaAccettato(utente);
	}

	@Override
	public double getPrezzoMassimoAccettato(UtenteDTO utente) throws SQLException {
		return PropostaVenditaDAO.getPrezzoMassimoAccettato(utente);
	}
	
	@Override
	public ArrayList<PropostaDTO> getProposteRicevute(UtenteDTO utente) throws SQLException {
		ArrayList<PropostaDTO> listaProposte = new ArrayList<PropostaDTO>();
		
		listaProposte.addAll(PropostaRegaloDAO.getProposteRegaloRicevute(utente));
		listaProposte.addAll(PropostaVenditaDAO.getProposteVenditaRicevute(utente));
		listaProposte.addAll(PropostaScambioDAO.getProposteScambioRicevute(utente));
		
		return listaProposte;
	}

	@Override
	public ArrayList<PropostaDTO> getProposteInviate(UtenteDTO utente) throws SQLException {
		ArrayList<PropostaDTO> listaProposte = new ArrayList<PropostaDTO>();

		listaProposte.addAll(PropostaRegaloDAO.getProposteRegaloInviate(utente));
		listaProposte.addAll(PropostaVenditaDAO.getProposteVenditaInviate(utente));
		listaProposte.addAll(PropostaScambioDAO.getProposteScambioInviate(utente));
		
		return listaProposte;
	}

	@Override
	public void creaPropostaRegalo(UtenteDTO utente, AnnuncioRegaloDTO annuncio, String messaggio) throws SQLException {
		PropostaRegaloDAO.creaPropostaRegalo(utente, annuncio, messaggio);
	}

	@Override
	public void accettaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaRegaloDAO.accettaPropostaRegalo(proposta);
	}

	@Override
	public void rifiutaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaRegaloDAO.rifiutaPropostaRegalo(proposta);
	}

	@Override
	public void modificaPropostaRegalo(PropostaRegaloDTO proposta, String messaggio) throws SQLException {
		PropostaRegaloDAO.modificaPropostaRegalo(proposta, messaggio);
	}

	@Override
	public void ritiraPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		PropostaRegaloDAO.ritiraPropostaRegalo(proposta);
	}

	@Override
	public void creaPropostaVendita(UtenteDTO utente, AnnuncioVenditaDTO annuncio, double prezzo) throws SQLException {
		PropostaVenditaDAO.creaPropostaVendita(utente, annuncio, prezzo);
	}

	@Override
	public void accettaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaVenditaDAO.accettaPropostaVendita(proposta);
	}

	@Override
	public void rifiutaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaVenditaDAO.rifiutaPropostaVendita(proposta);
	}

	@Override
	public void modificaPropostaVendita(PropostaVenditaDTO proposta, double prezzo) throws SQLException {
		PropostaVenditaDAO.modificaPropostaVendita(proposta, prezzo);
	}
	
	@Override
	public void ritiraPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		PropostaVenditaDAO.ritiraPropostaVendita(proposta);
	}

	@Override
	public void creaPropostaScambio(UtenteDTO utente, AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		PropostaScambioDAO.creaPropostaScambio(utente, annuncio, prodotti);
	}

	@Override
	public void accettaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaScambioDAO.accettaPropostaScambio(proposta);
	}

	@Override
	public void rifiutaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaScambioDAO.rifiutaPropostaScambio(proposta);
	}

	@Override
	public void modificaPropostaScambio(PropostaScambioDTO proposta, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		PropostaScambioDAO.modificaPropostaScambio(proposta, prodotti);
	}

	@Override
	public void ritiraPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		PropostaScambioDAO.ritiraPropostaScambio(proposta);
	}
}
