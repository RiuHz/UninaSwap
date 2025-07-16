package dao.aggiornaStatoAnnuncio;

import java.sql.Connection;
import java.sql.PreparedStatement;

import daoNew.DatabaseManager;

public class StatoAnnuncioDAOPostgre {

    public static void aggiornaStato(String tipoTabella, int idProdotto, String nuovoStato) {

        String enumType = "Stato_" + tipoTabella; //esce tipo Stato_Annuncio_Vendita passandogli Annuncio_Vendita

        String query = "UPDATE " + tipoTabella + " SET Stato = ?::" + enumType + " WHERE ID_Prodotto = ?";
        
        // La particolare sintassi "?::" è un cast per il valore dello stato nel dominio della tabella
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nuovoStato);//setta placeholder
            stmt.setInt(2, idProdotto);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
