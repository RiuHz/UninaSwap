package daoNew.annuncio;

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

class AnnuncioRegaloDAOPostgre extends DatabaseManager implements AnnuncioRegaloDAOInterface {
	
	@Override
	public ArrayList<AnnuncioRegaloDTO> getAnnunciRegalo(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<AnnuncioRegaloDTO> listaAnnunci = new ArrayList<AnnuncioRegaloDTO>();
		
		String query = """
					SELECT ID_Ann_Regalo, Consegna, Data, Stato,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione, Username,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
					FROM Annuncio_Regalo
					JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Username <> ? AND Stato = 'Attivo'
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			AnnuncioRegaloDTO annuncio = new AnnuncioRegaloDTO(
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
									result.getString("Username")
								)
					);
			
			listaAnnunci.add(annuncio);
		}
		
		return listaAnnunci;
	}

}
