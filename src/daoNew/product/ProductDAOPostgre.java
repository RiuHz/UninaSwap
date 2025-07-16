package daoNew.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import daoNew.DatabaseManager;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UserDTO;

public class ProductDAOPostgre extends DatabaseManager implements ProductDAOInterface {

	@Override
	public ArrayList<ProdottoDTO> getAllUserProduct(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
		
        ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
        
        String query = """
        			SELECT ID_Prodotto, Prodotto.Nome, Descrizione, Immagine, Username,
	        			Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
        			FROM Prodotto JOIN Categoria
        			ON Prodotto.ID_Categoria = Categoria.ID_Categoria
        			WHERE Username = ?
        		""";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, user.getUsername());
        
        ResultSet result = statement.executeQuery();
        
        while (result.next()) {
        	ProdottoDTO prodotto = new ProdottoDTO(
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
        	
        	listaProdotti.add(prodotto);
        }

		return listaProdotti;
	}

	@Override
	public ArrayList<ProdottoDTO> getAllProductInListings(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
        
        String query = """
        			SELECT ID_Prodotto, Prodotto.Nome, Descrizione, Immagine, Username
        				Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
        			FROM Prodotto JOIN Categoria
        			ON Prodotto.ID_Categoria = Categoria.ID_Categoria
        			WHERE Username = ? AND ID_Prodotto IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Regalo
        			) AND ID_Prodotto IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Vendita
        			) AND ID_Prodotto IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Scambio
        			)
        		""";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, user.getUsername());
        
        ResultSet result = statement.executeQuery();
        
        while (result.next()) {
        	ProdottoDTO prodotto = new ProdottoDTO(
        			result.getInt("ID_Prodotto"),
                    result.getString("Nome"),
                    result.getString("Descrizione"),
                    result.getBytes("Immagine"),
                    new CategoriaDTO(
                    			result.getInt("Categoria.ID_Categoria"),
                    			result.getString("Nome_Categoria")
                    		),
                    result.getString("Username")
                );
        	
        	listaProdotti.add(prodotto);
        }

		return listaProdotti;
	}

	@Override
	public ArrayList<ProdottoDTO> gettAllProductsNotInListings(UserDTO user) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
        
        String query = """
        			SELECT ID_Prodotto, Prodotto.Nome, Descrizione, Immagine, Username
        				Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
        			FROM Prodotto JOIN Categoria
        			ON Prodotto.ID_Categoria = Categoria.ID_Categoria
        			WHERE Username = ? AND ID_Prodotto NOT IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Regalo
        			) AND ID_Prodotto NOT IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Vendita
        			) AND ID_Prodotto NOT IN (
        				SELECT ID_Prodotto
        				FROM Annuncio_Scambio
        			)
        		""";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, user.getUsername());
        
        ResultSet result = statement.executeQuery();
        
        while (result.next()) {
        	ProdottoDTO prodotto = new ProdottoDTO(
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
        	
        	listaProdotti.add(prodotto);
        }

		return listaProdotti;
	}
}
