package daoNew.proposta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import daoNew.DatabaseManager;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UserDTO;
import dto.annunci.AnnuncioVenditaDTO;
import dto.proposte.PropostaVenditaDTO;

class PropostaVenditaDAOPostgre extends DatabaseManager implements PropostaVenditaDAOInterface {

	@Override
	public ArrayList<PropostaVenditaDTO> getProposteVendita(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaVenditaDTO> listaProposte = new ArrayList<PropostaVenditaDTO>();
		
		String query = """
					SELECT Proposta, Proposta_Vendita.Stato, Proposta_Vendita.Username AS Username_Proposta,
						Annuncio_Vendita.ID_Ann_Vendita, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Prezzo, Annuncio_Vendita.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione, Prodotto.Username AS Username_Prodotto,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
					FROM Proposta_Vendita
					JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
					JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Proposta_Vendita.Username <> ? AND Prodotto.Username = ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			PropostaVenditaDTO proposta = new PropostaVenditaDTO(
						result.getDouble("Proposta"),
						result.getString("Stato"),
						result.getString("Username_Proposta"),
						new AnnuncioVenditaDTO(
									result.getInt("ID_Ann_Vendita"),
									result.getString("Consegna_Annuncio"),
									result.getDate("Data_Annuncio"),
									result.getDouble("Prezzo"),
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
												result.getString("Username_Prodotto")
											)
								)
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}

	@Override
	public int getNumeroProposteVendita(UserDTO user) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Vendita
				JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
				JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
				WHERE Proposta_Vendita.Username <> ? AND Prodotto.Username = ?
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		return result.getInt("Count");
	}

}
