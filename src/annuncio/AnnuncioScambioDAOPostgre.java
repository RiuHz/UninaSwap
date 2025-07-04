package annuncio;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AnnuncioScambioDAOPostgre implements AnnuncioScambioDAOInterface {

    public static Integer getIdAnnuncioByProdottoId(int prodottoId) {
        String query = "SELECT ID_Ann_Scamb FROM Annuncio_Scambio WHERE ID_Prodotto = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prodottoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Ann_Scamb");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
