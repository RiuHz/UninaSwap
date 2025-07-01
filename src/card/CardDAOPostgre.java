package card;

import java.sql.*;
import java.util.Vector;

import db.DatabaseManager;
import entity.CardEntity;

public class CardDAOPostgre implements CardDAOInterface {

    @Override
    public Vector<CardEntity> getLast30Cards() {
        Vector<CardEntity> cards = new Vector<>();

        String query = """
            SELECT p.Nome, p.Immagine, p.Descrizione, c.Nome_Categoria,
                   COALESCE(s.Consegna, v.Consegna, r.Consegna) AS Consegna,
                   COALESCE(s.Stato::text, v.Stato::text, r.Stato::text) AS Tipo
            FROM Prodotto p
            JOIN Categoria c ON p.ID_Categoria = c.ID_Categoria
            LEFT JOIN Annuncio_Scambio s ON p.ID_Prodotto = s.ID_Prodotto
            LEFT JOIN Annuncio_Vendita v ON p.ID_Prodotto = v.ID_Prodotto
            LEFT JOIN Annuncio_Regalo r ON p.ID_Prodotto = r.ID_Prodotto
            ORDER BY p.ID_Prodotto DESC
            LIMIT 30
        """;

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                CardEntity card = new CardEntity(
                    rs.getString("Nome"),
                    rs.getBytes("Immagine"),
                    rs.getString("Consegna"),
                    rs.getString("Descrizione"),
                    rs.getString("Nome_Categoria"),
                    rs.getString("Tipo")
                );
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
