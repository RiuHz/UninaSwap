package dao.proposta.postgre;

import java.sql.Array;
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
import dto.annunci.AnnuncioScambioDTO;
import dto.proposte.PropostaScambioDTO;

class PropostaScambioDAOPostgre extends DatabaseManager {

	public ArrayList<PropostaScambioDTO> getProposteScambioRicevute(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaScambioDTO> listaProposte = new ArrayList<PropostaScambioDTO>();
		
		String query = """
					SELECT Proposta_Scambio.ID_Prop_Scambio, Proposta_Scambio.Stato,
						Annuncio_Scambio.ID_Ann_Scambio, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Scambio.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Scambio
					JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scambio = Annuncio_Scambio.ID_Ann_Scambio
					JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Proposta_Scambio.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Scambio.Username <> ? AND Prodotto.Username = ? AND Proposta_Scambio.Stato = 'In Attesa'
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			PropostaScambioDTO proposta = new PropostaScambioDTO(
						result.getInt("ID_Prop_Scambio"),
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
						new AnnuncioScambioDTO(
									result.getInt("ID_Ann_Scambio"),
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
								),
						getListaProdottiScambiati(result.getInt("ID_Prop_Scambio"))
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}
	
	public ArrayList<PropostaScambioDTO> getProposteScambioInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaScambioDTO> listaProposte = new ArrayList<PropostaScambioDTO>();
		
		String query = """
					SELECT Proposta_Scambio.ID_Prop_Scambio, Proposta_Scambio.Stato,
						Annuncio_Scambio.ID_Ann_Scambio, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Scambio.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Proposta_Scambio
					JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scambio = Annuncio_Scambio.ID_Ann_Scambio
					JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Proposta_Scambio.Username = ? AND Prodotto.Username <> ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			PropostaScambioDTO proposta = new PropostaScambioDTO(
						result.getInt("ID_Prop_Scambio"),
						result.getString("Stato"),
						utente,
						new AnnuncioScambioDTO(
									result.getInt("ID_Ann_Scambio"),
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
								),
						getListaProdottiScambiati(result.getInt("ID_Prop_Scambio"))
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}
	
	private ArrayList<ProdottoDTO> getListaProdottiScambiati(int ID_Prop_Scambio) throws SQLException {
		Connection db = getConnection();
		
		ArrayList<ProdottoDTO> listaProdottiScambiati = new ArrayList<ProdottoDTO>();
		
		String query = """
					SELECT ID_Prodotto, Prodotto.Nome, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Prodotto.ID_Prop_Scambio = ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(1, ID_Prop_Scambio);
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			ProdottoDTO prodottoScambiato = new ProdottoDTO(
						result.getInt("ID_Prodotto"),
						result.getString("Nome"),
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
					);
			
			listaProdottiScambiati.add(prodottoScambiato);
		}
        
        return listaProdottiScambiati;
	}
	
	public int getNumeroProposteScambioInviate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Scambio
				WHERE Username = ?
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}

	public int getNumeroProposteScambioAccettate(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Scambio
				WHERE Username = ? AND Stato = 'Accettata'
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		result.next();
		
		return result.getInt("Count");
	}

	public void creaPropostaScambio(UtenteDTO utente, AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		Connection db = getConnection();
		
		String query = "CALL inserisci_proposta_scambio(?, ?, ?)";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setInt(2, annuncio.getId());		
		statement.setArray(3, getArrayIdProdotti(db, prodotti));
		
		statement.executeUpdate();
				
		db.close();
	}
	
	private Array getArrayIdProdotti(Connection db, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		ArrayList<Integer> idProdotti = new ArrayList<Integer>();
		
		for (ProdottoDTO prodotto : prodotti)
			idProdotti.add(prodotto.getId());
		
		return db.createArrayOf("INTEGER", idProdotti.toArray());
	}

	public void accettaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		Connection db = getConnection();
		
		// TODO Test
		
		System.out.println("Sono qui dio cane");

		String query = """
					UPDATE Proposta_Scambio
					SET Stato = 'Accettata'
					WHERE ID_Prop_Scambio = ?;
					
					UPDATE Annuncio_Scambio
					SET Stato = 'Scambiato'
					WHERE ID_Ann_Scambio = ?;
					
					UPDATE Proposta_Scambio
					SET Stato = 'Rifiutata'
					WHERE ID_Prop_Scambio != ? AND ID_Ann_Scambio = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(1, proposta.getId());
		statement.setInt(2, proposta.getAnnuncio().getId());
		statement.setInt(3, proposta.getId());
		statement.setInt(4, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

	public void rifiutaPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Proposta_Scambio
					SET Stato = 'Rifiutata'
					WHERE ID_Prop_Scambio = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(1, proposta.getId());
		
		statement.executeUpdate();
		
		db.close();
	}

	public void modificaPropostaScambio(PropostaScambioDTO proposta, ArrayList<ProdottoDTO> prodotti) throws SQLException {
		Connection db = getConnection();

		String query = """
					UPDATE Prodotto
					SET ID_Prop_Scambio = NULL
					WHERE ID_Prodotto IN (
							SELECT ID_Prodotto
							FROM Prodotto
							WHERE ID_Prop_Scambio = ?
						);
						
					UPDATE Prodotto
					SET ID_Prop_Scambio = ?
					WHERE ID_Prodotto IN (
				""";
		
		query = aggiungiSegnaposti(query, prodotti.size());
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(1, proposta.getId());
		statement.setInt(2, proposta.getId());
		
		for (int i = 0; i < prodotti.size(); i++)
			statement.setInt(i + 3, prodotti.get(i).getId());
		
		statement.executeUpdate();
		
		db.close();
	}
	
	private String aggiungiSegnaposti(String query, int numeroSegnaposti) {
		
		for (int i = 0; i < numeroSegnaposti; i++)
			query = query + "?, ";
		
		query = query.substring(0, query.length() - 2);
		query = query + ")";
		
		return query;
	}

	public void ritiraPropostaScambio(PropostaScambioDTO proposta) throws SQLException {
		Connection db = getConnection();

		String query = """
					DELETE FROM Proposta_Regalo
					WHERE ID_Prop_Scambio = ?;
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setInt(1, proposta.getAnnuncio().getId());
		
		statement.executeUpdate();
		
		db.close();
	}

}
