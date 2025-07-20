package dao.prodotto.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DatabaseManager;
import dao.prodotto.ProdottoDAOInterface;
import dto.CategoriaDTO;
import dto.ProdottoDTO;
import dto.UniversitaDTO;
import dto.UtenteDTO;

public class ProdottoDAOPostgre extends DatabaseManager implements ProdottoDAOInterface {

	@Override
	public ArrayList<ProdottoDTO> getProdottiUtente(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
        
        String query = """
        			SELECT ID_Prodotto, Prodotto.Nome, Descrizione, Immagine, Prodotto.Username,
	        			Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria,
	        			Utente.Nome AS Nome_Utente, Utente.Cognome,
	        			Universita.ID_Universita, Universita.Nome AS Nome_Universita
        			FROM Prodotto
        			JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
        			JOIN Utente ON Prodotto.Username = Utente.Username
        			JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
        			WHERE Utente.Username = ?
        				AND ID_Prodotto NOT IN (
        					SELECT ID_Prodotto
        					FROM Annuncio_Regalo
        					WHERE Stato = 'Regalato'
        				) AND ID_Prodotto NOT IN (
        					SELECT ID_Prodotto
        					FROM Annuncio_Vendita
        					WHERE Stato = 'Venduto'
        				) AND ID_Prodotto NOT IN (
        					SELECT ID_Prodotto
        					FROM Annuncio_Scambio
        					WHERE Stato = 'Scambiato'
        				) AND (
								Prodotto.ID_Prop_Scambio IS NULL OR Prodotto.ID_Prop_Scambio NOT IN (
								SELECT Proposta_Scambio.ID_Prop_Scambio
								FROM Proposta_Scambio
								WHERE Stato = 'Accettata'
							)
						)
        		""";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, utente.getUsername());
        
        ResultSet result = statement.executeQuery();
        
        db.close();
        
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
        	
        	listaProdotti.add(prodotto);
        }

		return listaProdotti;
	}

	@Override
	public ArrayList<ProdottoDTO> getProdottiElegibili(UtenteDTO utente) throws SQLException {
		Connection db = getConnection();
		
        ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
        
        String query = """
    			SELECT ID_Prodotto, Prodotto.Nome, Descrizione, Immagine,
    				Categoria.ID_Categoria, Categoria.Nome AS Nome_Categoria
    			FROM Prodotto
    			JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
    			WHERE Username = ? AND
        			ID_Prodotto NOT IN (
	    				SELECT ID_Prodotto
	    				FROM Annuncio_Regalo
	    			) AND ID_Prodotto NOT IN (
	    				SELECT ID_Prodotto
	    				FROM Annuncio_Vendita
	    			) AND ID_Prodotto NOT IN (
	    				SELECT ID_Prodotto
	    				FROM Annuncio_Scambio
	    			) AND (
        				ID_Prop_Scambio IS NULL OR ID_Prop_Scambio NOT IN
        		  			(
        		  				SELECT ID_Prop_Scambio
        		  				FROM Proposta_Scambio
        		  				WHERE Stato = 'In Attesa' OR Stato = 'Accettata'
    		  				)
        			)
    		""";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, utente.getUsername());
        
        ResultSet result = statement.executeQuery();
        
        db.close();
        
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
                    utente
                );
        	
        	listaProdotti.add(prodotto);
        }
        
		return listaProdotti;
	}

	@Override
	public void creaProdotto(UtenteDTO utente, String nome, byte[] immagine, String descrizione, CategoriaDTO categoria) throws SQLException {
		Connection db = getConnection();
        
        String query = "INSERT INTO Prodotto (Nome, Immagine, Descrizione, Username, ID_Categoria) VALUES (?, ?, ?, ?, ?)";
        
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, nome);
        statement.setBytes(2, immagine);
        statement.setString(3, descrizione);
        statement.setString(4, utente.getUsername());
        statement.setInt(5, categoria.getId());
        
        statement.executeUpdate();
        
        db.close();
	}
	
}
