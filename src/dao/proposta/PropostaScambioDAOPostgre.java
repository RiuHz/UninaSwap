package dao.proposta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

import daoNew.DatabaseManager;

public class PropostaScambioDAOPostgre implements PropostaScambioDAOInterface {

    // Controlla se l'utente ha già fatto una proposta per quell'annuncio
    public static boolean propostaEsistente(int idAnnuncioScambio, String username) {
        String query = "SELECT 1 FROM Proposta_Scambio WHERE ID_Ann_Scamb = ? AND Username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAnnuncioScambio);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Inserisce una proposta se non esiste già
    public static boolean inserisciProposta(int idAnnuncioScambio, String username, List<Integer> idProdottiOfferti) {
        String insertProposta = "INSERT INTO Proposta_Scambio (ID_Ann_Scamb, Username, Stato) VALUES (?, ?, 'In_Attesa') RETURNING ID_Prop_Scamb";
        String updateProdotto = "UPDATE Prodotto SET ID_Prop_Scamb = ? WHERE ID_Prodotto = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false); // inizio transazione

            int idPropostaGenerato = -1;

            try (PreparedStatement stmt = conn.prepareStatement(insertProposta)) {
                stmt.setInt(1, idAnnuncioScambio);
                stmt.setString(2, username);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idPropostaGenerato = rs.getInt("ID_Prop_Scamb");
                } else {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateProdotto)) {
                for (Integer idProdotto : idProdottiOfferti) {
                    updateStmt.setInt(1, idPropostaGenerato);
                    updateStmt.setInt(2, idProdotto);
                    updateStmt.addBatch();
                }
                updateStmt.executeBatch();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
