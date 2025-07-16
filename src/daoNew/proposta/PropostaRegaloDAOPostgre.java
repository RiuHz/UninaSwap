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
import dto.annunci.AnnuncioRegaloDTO;
import dto.proposte.PropostaRegaloDTO;

class PropostaRegaloDAOPostgre extends DatabaseManager implements PropostaRegaloDAOInterface {

	@Override
	public ArrayList<PropostaRegaloDTO> getProposteRegalo(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaRegaloDTO> listaProposte = new ArrayList<PropostaRegaloDTO>();
		
		String query = """
					SELECT Messaggio, Proposta_Regalo.Stato, Proposta_Regalo.Username AS Username_Proposta,
						Annuncio_Regalo.ID_Ann_Regalo, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Regalo.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione, Prodotto.Username AS Username_Prodotto,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
					FROM Proposta_Regalo
					JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Proposta_Regalo.Username <> ? AND Prodotto.Username = ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			PropostaRegaloDTO proposta = new PropostaRegaloDTO(
						result.getString("Stato"),
						result.getString("Messaggio"),
						result.getString("Username_Proposta"),
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
												result.getString("Username_Prodotto")
											)
								)
					);
			
			listaProposte.add(proposta);
		}
		
		return listaProposte;
	}

	@Override
	public int getNumeroProposteRegalo(UserDTO user) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Regalo
				JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
				JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
				WHERE Proposta_Regalo.Username <> ? AND Prodotto.Username = ?
			""";
	
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		return result.getInt("Count");
	}

}
