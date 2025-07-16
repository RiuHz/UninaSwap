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
import dto.annunci.AnnuncioScambioDTO;

class AnnuncioScambioDAOPostgre extends DatabaseManager implements AnnuncioScambioDAOInterface {

	@Override
	public ArrayList<AnnuncioScambioDTO> getAnnunciScambio(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<AnnuncioScambioDTO> listaAnnunci = new ArrayList<AnnuncioScambioDTO>();
		
		String query = """
					SELECT ID_Ann_Scambio, Consegna, Data, Stato,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione, Username,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
					FROM Annuncio_Scambio
					JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Username <> ? AND Stato = 'Attivo'
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			AnnuncioScambioDTO annuncio = new AnnuncioScambioDTO(
						result.getInt("ID_Ann_Scambio"),
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
