package proposta;

import db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PropostaVenditaDAOPostgre implements PropostaVenditaInterface {

    public static boolean propostaEsistente(int idAnnuncioVendita, String username) {
        String query = "SELECT 1 FROM Proposta_Vendita WHERE ID_Ann_Vendita = ? AND Username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAnnuncioVendita);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean inserisciProposta(int idAnnuncioVendita, String username, double proposta) {
        // Controllo preventivo
        if (propostaEsistente(idAnnuncioVendita, username)) {
            return false; // già esistente  non procedere
        }

        String query = "INSERT INTO Proposta_Vendita (ID_Ann_Vendita, Username, Stato, Proposta) VALUES (?, ?, 'In_Attesa', ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAnnuncioVendita);
            stmt.setString(2, username);
            stmt.setDouble(3, proposta);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
