package dao.proposta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import daoNew.DatabaseManager;

public class PropostaRegaloDAOPostgre implements PropostaRegaloDAOInterface {

    public static boolean propostaEsistente(int idAnnuncioRegalo, String username) {
        String query = "SELECT 1 FROM Proposta_Regalo WHERE ID_Ann_Regalo = ? AND Username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAnnuncioRegalo);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se esiste almeno una rcga
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean inserisciProposta(int idAnnuncioRegalo, String username, String messaggio) {
        String query = "INSERT INTO Proposta_Regalo (ID_Ann_Regalo, Username, Stato, Messaggio) VALUES (?, ?, 'In_Attesa', ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAnnuncioRegalo);
            stmt.setString(2, username);
            stmt.setString(3, messaggio);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
