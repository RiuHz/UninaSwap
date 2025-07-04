package tipoAnnuncio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import db.DatabaseManager;

public class TipoAnnuncioDAOPostgre implements TipoAnnuncioDAOInterface {

    public String getTipoByProdottoId(int idProdotto) {
        String queryVendita = "SELECT 1 FROM Annuncio_Vendita WHERE ID_Prodotto = ?";
        String queryRegalo = "SELECT 1 FROM Annuncio_Regalo WHERE ID_Prodotto = ?";
        String queryScambio = "SELECT 1 FROM Annuncio_Scambio WHERE ID_Prodotto = ?";

        try (Connection conn = DatabaseManager.getConnection()) {

            try (PreparedStatement stmt = conn.prepareStatement(queryVendita)) {
                stmt.setInt(1, idProdotto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return "VENDITA";
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(queryRegalo)) {
                stmt.setInt(1, idProdotto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return "REGALO";
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(queryScambio)) {
                stmt.setInt(1, idProdotto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return "SCAMBIO";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "NESSUNO";
    }
}
