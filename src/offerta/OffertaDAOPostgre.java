package offerta;

import db.DatabaseManager;
import entity.OffertaEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffertaDAOPostgre implements OffertaDAOPostgreInterface {

    @Override
    public List<OffertaEntity> getOfferteRicevute(String usernameDestinatario) throws SQLException {
        List<OffertaEntity> offerte = new ArrayList<>();

        String query="""
        SELECT 'Vendita' AS tipo, Prodotto.Nome, Prodotto.Immagine, Proposta_Vendita.Username, NULL AS messaggio, Proposta_Vendita.Proposta, Proposta_Vendita.Stato
        FROM Proposta_Vendita
        JOIN Annuncio_Vendita ON Proposta_Vendita.ID_Ann_Vendita = Annuncio_Vendita.ID_Ann_Vendita
        JOIN Prodotto ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
        WHERE Prodotto.Username = ?

        UNION ALL

        SELECT 'Regalo', Prodotto.Nome, Prodotto.Immagine, Proposta_Regalo.Username, Proposta_Regalo.Messaggio, NULL, Proposta_Regalo.Stato
        FROM Proposta_Regalo
        JOIN Annuncio_Regalo ON Proposta_Regalo.ID_Ann_Regalo = Annuncio_Regalo.ID_Ann_Regalo
        JOIN Prodotto ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
        WHERE Prodotto.Username = ?

        UNION ALL

        SELECT 'Scambio', Prodotto.Nome, Prodotto.Immagine, Proposta_Scambio.Username, NULL, NULL, Proposta_Scambio.Stato
        FROM Proposta_Scambio
        JOIN Annuncio_Scambio ON Proposta_Scambio.ID_Ann_Scamb = Annuncio_Scambio.ID_Ann_Scamb
        JOIN Prodotto ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
        WHERE Prodotto.Username = ?
        	""";


        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Username ripetuto 3 volte per i 3 blocchi di query
            stmt.setString(1, usernameDestinatario);
            stmt.setString(2, usernameDestinatario);
            stmt.setString(3, usernameDestinatario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OffertaEntity offerta = new OffertaEntity(
                    rs.getString("tipo"),
                    rs.getString("Nome"),
                    rs.getBytes("Immagine"),
                    rs.getString("Username"),
                    rs.getString("messaggio"),
                    rs.getObject("Proposta") != null ? rs.getDouble("Proposta") : null,
                    rs.getString("Stato")
                );
                offerte.add(offerta);
            }
        }

        return offerte;
    }
}
