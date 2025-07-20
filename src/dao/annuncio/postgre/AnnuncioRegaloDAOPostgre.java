package dao.annuncio.postgre;

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
import dto.annunci.AnnuncioDTO;
import dto.annunci.AnnuncioRegaloDTO;

class AnnuncioRegaloDAOPostgre extends DatabaseManager {
	
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecenti(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<AnnuncioDTO> listaAnnunciRecenti = new ArrayList<AnnuncioDTO>();
		
		String query = """
					SELECT ID_Ann_Regalo, Consegna, Data, Stato,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Annuncio_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Prodotto.Username <> ? AND Stato = 'Attivo' AND ID_Ann_Regalo NOT IN (
						SELECT Proposta_Regalo.ID_Ann_Regalo
						FROM Proposta_Regalo
						WHERE Username = ? AND Stato = 'In Attesa'
					)
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			AnnuncioDTO annuncio = new AnnuncioRegaloDTO(
						result.getInt("ID_Ann_Regalo"),
						result.getString("Consegna"),
						result.getDate("Data"),
						result.getString("Stato"),
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
					);
			
			listaAnnunciRecenti.add(annuncio);
		}
		
		return listaAnnunciRecenti;
	}
	
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<AnnuncioDTO> listaAnnunciRecenti = new ArrayList<AnnuncioDTO>();
		
		String query = """
					SELECT ID_Ann_Regalo, Consegna, Data, Stato,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
						Utente.Nome AS Nome_Utente, Cognome, Utente.Username,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Annuncio_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					JOIN Utente ON Prodotto.Username = Utente.Username
					JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Prodotto.Username <> ? AND Stato = 'Attivo' AND Categoria.ID_Categoria IN (
				""";
		
		query = aggiungiSegnaposti(query, categorie.size());
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, utente.getUsername());
		statement.setString(2, utente.getUsername());
		
		for (int i = 0; i < categorie.size(); i++)
			statement.setInt(i + 2, categorie.get(i));
		
		ResultSet result = statement.executeQuery();
		
		db.close();
		
		while (result.next()) {
			AnnuncioDTO annuncio = new AnnuncioRegaloDTO(
						result.getInt("ID_Ann_Regalo"),
						result.getString("Consegna"),
						result.getDate("Data"),
						result.getString("Stato"),
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
															result.getInt("ID_Unviersita"),
															result.getString("Nome_Universita")
														)
											)
								)
					);
			
			listaAnnunciRecenti.add(annuncio);
		}
		
		return listaAnnunciRecenti;
	}
	
	private String aggiungiSegnaposti(String query, int numeroSegnaposti) {
		
		for (int i = 0; i < numeroSegnaposti; i++)
			query = query + "?, ";
		
		query = query.substring(0, query.length() - 2);
		query = query + ")";
		
		return query;
	}
	
	public void creaAnnuncioRegalo(ProdottoDTO prodotto, String consegna) throws SQLException {
		Connection db = getConnection();
		
		String query = "INSERT INTO Annuncio_Regalo (Consegna, Stato, ID_Prodotto) VALUES (?, 'Attivo', ?)";
		
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, consegna);
        statement.setInt(2, prodotto.getId());
        
        statement.executeUpdate();
        
        db.close();
	}

}
