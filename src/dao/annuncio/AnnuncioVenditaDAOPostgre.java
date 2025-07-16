package dao.annuncio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import daoNew.DatabaseManager;

public class AnnuncioVenditaDAOPostgre implements AnnuncioVenditaDAOInterface  {

	 public static Integer getIdAnnuncioByProdottoId(int prodottoId) {
	        String query = "SELECT ID_Ann_Vendita FROM Annuncio_Vendita WHERE ID_Prodotto = ?";
	        try (Connection conn = DatabaseManager.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setInt(1, prodottoId);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("ID_Ann_Vendita");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
