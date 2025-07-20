package dao.proposta.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DatabaseManager;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UniversitaDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaVenditaDTO;

class PropostaVenditaDAOPostgre extends DatabaseManager {

	public ArrayList<PropostaVenditaDTO> getProposteVenditaRicevute(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaVenditaDTO> listaProposte = new ArrayList<PropostaVenditaDTO>();
		
		String query = """
					SELECT Proposta, Proposta_Vendita.Stato,
						Annuncio_Vendita.ID_Ann_Vendita, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Prezzo, Annuncio_Vendita.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Vendita
					JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
					JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Proposta_Vendita.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Vendita.Username <> ? AND Prodotto.Username = ? AND Proposta_Vendita.Stato = 'In Attesa'
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			PropostaVenditaDTO proposta = new PropostaVenditaDTO(
					result.getDouble("Proposta"),
					result.getString("Stato"),
					new UtenteDTO(
								result.getString("Nome_Utente"),
								result.getString("Cognome"),
								result.getString("Username"),
								new UniversitaDTO(
											result.getInt("ID_Universita"),
											result.getString("Nome_Universita")
										)
							),
					new AnnuncioVenditaDTO(
								result.getInt("ID_Ann_Vendita"),
								result.getString("Consegna_Annuncio"),
								result.getDate("Data_Annuncio"),
								result.getDouble("prezzo"),
								result.getString("Stato_Annuncio"),
								new ProdottoDTO(
											result.getInt("ID_Prodotto"),
											result.getString("Nome_Prodotto"),
											result.getString("Descrizione"),
											result.getBytes("Immagine"),
											new CategoriaDTO(
														result.getInt("ID_Categoria"),
														result.getString("Nome_Categoria")
													),
											utente
										)
							)
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}
	
	public ArrayList<PropostaVenditaDTO> getProposteVenditaInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaVenditaDTO> listaProposte = new ArrayList<PropostaVenditaDTO>();
		
		String query = """
					SELECT Proposta, Proposta_Vendita.Stato,
						Annuncio_Vendita.ID_Ann_Vendita, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Prezzo, Annuncio_Vendita.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Vendita
					JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
					JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Vendita.Username = ? AND Prodotto.Username <> ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			PropostaVenditaDTO proposta = new PropostaVenditaDTO(
					result.getDouble("Proposta"),
					result.getString("Stato"),
					utente,
					new AnnuncioVenditaDTO(
								result.getInt("ID_Ann_Vendita"),
								result.getString("Consegna_Annuncio"),
								result.getDate("Data_Annuncio"),
								result.getDouble("prezzo"),
								result.getString("Stato_Annuncio"),
								new ProdottoDTO(
											result.getInt("ID_Prodotto"),
											result.getString("Nome_Prodotto"),
											result.getString("Descrizione"),
											result.getBytes("Immagine"),
											new CategoriaDTO(
														result.getInt("ID_Categoria"),
														result.getString("Nome_Categoria")
													),
											new UtenteDTO(
													result.getString("Nome_Utente"),
													result.getString("Cognome"),
													result.getString("Username"),
													new UniversitaDTO(
																result.getInt("ID_Universita"),
																result.getString("Nome_Universita")
															)
												)
										)
							)
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}
	
	public int getNumeroProposteVenditaInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Vendita
				WHERE Username = ?
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}

	public int getNumeroProposteVenditaAccettate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Vendita
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}
	
	public double getPrezzoMinimoAccettato(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT MIN(Proposta) AS Minimo
				FROM Proposta_Vendita
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		double minimo = result.getDouble("Minimo");
		
		return (Double.isNaN(minimo) ? 0.0 : minimo);
	}

	public double getPrezzoMediaAccettato(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT AVG(Proposta) AS Media
				FROM Proposta_Vendita
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		double media = result.getDouble("Media");
		
		return (Double.isNaN(media) ? 0.0 : media);
	}

	public double getPrezzoMassimoAccettato(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT MAX(Proposta) AS Massimo
				FROM Proposta_Vendita
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		double massimo = result.getDouble("Massimo");
		
		return (Double.isNaN(massimo) ? 0.0 : massimo);
	}
	
	public void creaPropostaVendita(UtenteDTO utente, AnnuncioVenditaDTO annuncio, double prezzo) throws SQLException {
		Connection db = getConnection();

		String query = "INSERT INTO Proposta_Vendita (Stato, Proposta, Username, ID_Ann_Vendita) VALUES ('In Attesa', ?, ?, ?)";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setDouble(1, prezzo);
		statement.setString(2, utente.getUsername());
		statement.setInt(3, annuncio.getId());
		
		statement.executeUpdate();
		
		db.close();
	}

	public void accettaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Vendita
					SET Stato = 'Accettata'
					WHERE Username = ? AND ID_Ann_Vendita = ?;
					
					UPDATE Annuncio_Vendita
					SET Stato = 'Venduto'
					WHERE ID_Ann_Vendita = ?;
					
					UPDATE Proposta_Vendita
					SET Stato = 'Rifiutata'
					WHERE Username != ? AND ID_Ann_Vendita = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, proposta.getUtente().getUsername());
		statement.setInt(2, proposta.getAnnuncio().getId());
		statement.setInt(3, proposta.getAnnuncio().getId());
		statement.setString(4, proposta.getUtente().getUsername());
		statement.setInt(5, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

	public void rifiutaPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Vendita
					SET Stato = 'Rifiutata'
					WHERE Username = ? AND ID_Ann_Vendita = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, proposta.getUtente().getUsername());
		statement.setInt(2, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

	public void modificaPropostaVendita(PropostaVenditaDTO proposta, double prezzo) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Vendita
					SET Proposta = ?
					WHERE Username = ? AND ID_Ann_Vendita = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setDouble(1, prezzo);
		statement.setString(2, proposta.getUtente().getUsername());
		statement.setInt(3, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}
	
	public void ritiraPropostaVendita(PropostaVenditaDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					DELETE FROM Proposta_Vendita
					WHERE Username = ? AND ID_Ann_Vendita = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, proposta.getUtente().getUsername());
		statement.setInt(2, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

}
