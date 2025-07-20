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
import dto.annunci.AnnuncioRegaloDTO;
import dto.proposte.PropostaRegaloDTO;

class PropostaRegaloDAOPostgre extends DatabaseManager {

	public ArrayList<PropostaRegaloDTO> getProposteRegaloRicevute(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaRegaloDTO> listaProposte = new ArrayList<PropostaRegaloDTO>();
		
		String query = """
					SELECT Messaggio, Proposta_Regalo.Stato,
						Annuncio_Regalo.ID_Ann_Regalo, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Regalo.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Regalo
					JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Proposta_Regalo.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Regalo.Username <> ? AND Prodotto.Username = ? AND Proposta_Regalo.Stato = 'In Attesa'
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			PropostaRegaloDTO proposta = new PropostaRegaloDTO(
						result.getString("Stato"),
						result.getString("Messaggio"),
						new UtenteDTO(
									result.getString("Nome_Utente"),
									result.getString("Cognome"),
									result.getString("Username"),
									new UniversitaDTO(
												result.getInt("ID_Universita"),
												result.getString("Nome_Universita")
											)
								),
						new AnnuncioRegaloDTO(
									result.getInt("ID_Ann_Regalo"),
									result.getString("Consegna_Annuncio"),
									result.getDate("Data_Annuncio"),
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
	
	public ArrayList<PropostaRegaloDTO> getProposteRegaloInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaRegaloDTO> listaProposte = new ArrayList<PropostaRegaloDTO>();
		
		String query = """
					SELECT Messaggio, Proposta_Regalo.Stato,
						Annuncio_Regalo.ID_Ann_Regalo, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Regalo.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Regalo
					JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Regalo.Username = ? AND Prodotto.Username <> ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			PropostaRegaloDTO proposta = new PropostaRegaloDTO(
					result.getString("Stato"),
					result.getString("Messaggio"),
					utente,
					new AnnuncioRegaloDTO(
								result.getInt("ID_Ann_Regalo"),
								result.getString("Consegna_Annuncio"),
								result.getDate("Data_Annuncio"),
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
	
	public int getNumeroProposteRegaloInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Regalo
				WHERE Username = ?
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}
	
	public int getNumeroProposteRegaloAccettate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Regalo
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}
	
	public void creaPropostaRegalo(UtenteDTO utente, AnnuncioRegaloDTO annuncio, String messaggio) throws SQLException {
		Connection db = getConnection();

		String query = "INSERT INTO Proposta_Regalo (Stato, Messaggio, Username, ID_Ann_Regalo) VALUES ('In Attesa', ?, ?, ?)";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, messaggio);
		statement.setString(2, utente.getUsername());
		statement.setInt(3, annuncio.getId());
		
		statement.executeUpdate();
		
		db.close();
	}
	
	public void accettaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Regalo
					SET Stato = 'Accettata'
					WHERE Username = ? AND ID_Ann_Regalo = ?;
					
					UPDATE Annuncio_Regalo
					SET Stato = 'Regalato'
					WHERE ID_Ann_Regalo = ?;
					
					UPDATE Proposta_Regalo
					SET Stato = 'Rifiutata'
					WHERE Username != ? AND ID_Ann_Regalo = ?;
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
	
	public void rifiutaPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Regalo
					SET Stato = 'Rifiutata'
					WHERE Username = ? AND ID_Ann_Regalo = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, proposta.getUtente().getUsername());
		statement.setInt(2, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}
	
	public void modificaPropostaRegalo(PropostaRegaloDTO proposta, String messaggio) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Regalo
					SET Messaggio = ?
					WHERE Username = ? AND ID_Ann_Regalo = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, messaggio);
		statement.setString(2, proposta.getUtente().getUsername());
		statement.setInt(3, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}
	
	public void ritiraPropostaRegalo(PropostaRegaloDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					DELETE FROM Proposta_Regalo
					WHERE Username = ? AND ID_Ann_Regalo = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, proposta.getUtente().getUsername());
		statement.setInt(2, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

}
