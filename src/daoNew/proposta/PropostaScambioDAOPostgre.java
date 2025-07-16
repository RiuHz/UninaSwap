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
import dto.annunci.AnnuncioScambioDTO;

import dto.proposte.PropostaScambioDTO;

class PropostaScambioDAOPostgre extends DatabaseManager implements PropostaScambioDAOInterface {

	@Override
	public ArrayList<PropostaScambioDTO> getProposteScambio(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<PropostaScambioDTO> listaProposte = new ArrayList<PropostaScambioDTO>();
		
		String query = """
					SELECT Proposta_Scambio.Stato, Proposta_Scambio.Username AS Username_Proposta,
						Annuncio_Scambio.ID_Ann_Scambio, Consegna AS Consegna_Annuncio, Data AS Data_Annuncio, Annuncio_Scambio.Stato AS Stato_Annuncio,
						Prodotto.ID_Prodotto, Prodotto.Nome AS Nome_Prodotto, Immagine, Descrizione, Prodotto.Username AS Username_Prodotto,
						Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
					FROM Proposta_Scambio
					JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scambio = Annuncio_Scambio.ID_Ann_Scambio
					JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Proposta_Scambio.Username <> ? AND Prodotto.Username = ?
				""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			PropostaScambioDTO proposta = new PropostaScambioDTO(
						result.getInt("ID_Prop_Scambio"),
						result.getString("Stato"),
						result.getString("Username_Proposta"),
						new AnnuncioScambioDTO(
									result.getInt("ID_Ann_Vendita"),
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
					SELECT ID_Prodotto, Nome, Immagine, Descrizione, Username
						ID_Categoria, Categoria.Nome AS Categoria
					FROM Prodotto
					JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
					WHERE Prodotto.ID_Prop_Scambio = 
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
						result.getString("Username")
					);
			
			listaProdottiScambiati.add(prodottoScambiato);
		}
        
        return listaProdottiScambiati;
	}

	@Override
	public int getNumeroProposteScambio(UserDTO user) throws SQLException {
		Connection db = getConnection();

		String query = """
				SELECT COUNT(*) AS Count
				FROM Proposta_Scambio
				JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scambio = Annuncio_Scambio.ID_Ann_Scambio
				JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
				WHERE Proposta_Scambio.Username <> ? AND Prodotto.Username = ?
			""";
		
		PreparedStatement statement = db.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getUsername());
		
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		return result.getInt("Count");
	}

}
