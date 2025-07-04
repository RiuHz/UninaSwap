package aggiornaStatoAnnuncio;

import db.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AggiornaStatoAnnuncioDAOPostgre {

    public static void aggiornaStato(String tipoTabella, int idProdotto, String nuovoStato) {

        String enumType = "Stato_" + tipoTabella;//esce tipo Stato_Annuncio_Vendita passandogli Annuncio_Vendita

        String query = "UPDATE " + tipoTabella + " SET Stato = ?::" + enumType + " WHERE ID_Prodotto = ?";
        //la particolare sintatti ?:: è un casto serve a castare il valore dello stato nel tipo della tabella
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
