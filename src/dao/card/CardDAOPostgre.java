package dao.card;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;

import daoNew.DatabaseManager;

import java.util.List;

import entity.CardEntity;

public class CardDAOPostgre implements CardDAOInterface {

    @Override
    public Vector<CardEntity> getLast30Cards() {
        Vector<CardEntity> cards = new Vector<>();
        //le 3 virgolette iniziali e finali servono per scrivere stringhe su più righe in modo più leggibile e senza dover concatenare
        //COALESCENSE restituisce il primo valore NOT NULL tra quelli indicati
        String query = """
        	    SELECT Prodotto.Nome,
        	           Prodotto.Immagine,
        	           Prodotto.Descrizione,
        	           Categoria.Nome_Categoria,
        	           COALESCE(Annuncio_Scambio.Consegna, Annuncio_Vendita.Consegna, Annuncio_Regalo.Consegna) AS Consegna,
        	           COALESCE(Annuncio_Scambio.Stato::text, Annuncio_Vendita.Stato::text, Annuncio_Regalo.Stato::text) AS Tipo,
        	           COALESCE(Annuncio_Scambio.ID_Prodotto::text, Annuncio_Vendita.ID_Prodotto::text, Annuncio_Regalo.ID_Prodotto::text) AS prodotto_id,
        	           Prodotto.Username
        	    FROM Prodotto
        	    JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
        	    LEFT JOIN Annuncio_Scambio ON Prodotto.ID_Prodotto = Annuncio_Scambio.ID_Prodotto
        	    LEFT JOIN Annuncio_Vendita ON Prodotto.ID_Prodotto = Annuncio_Vendita.ID_Prodotto
        	    LEFT JOIN Annuncio_Regalo ON Prodotto.ID_Prodotto = Annuncio_Regalo.ID_Prodotto
        	    LEFT JOIN Proposta_Scambio ON Annuncio_Scambio.ID_Ann_Scamb = Proposta_Scambio.ID_Ann_Scamb
        	    LEFT JOIN Proposta_Vendita ON Annuncio_Vendita.ID_Ann_Vendita = Proposta_Vendita.ID_Ann_Vendita
        	    LEFT JOIN Proposta_Regalo ON Annuncio_Regalo.ID_Ann_Regalo = Proposta_Regalo.ID_Ann_Regalo
        	    WHERE
        	        Annuncio_Scambio.Stato = 'Attivo'
        	        OR Annuncio_Vendita.Stato = 'Attivo'
        	        OR Annuncio_Regalo.Stato = 'Attivo'
        	        OR Proposta_Scambio.Stato IN ('In_Attesa', 'Rifiutata')
        	        OR Proposta_Vendita.Stato IN ('In_Attesa', 'Rifiutata')
        	        OR Proposta_Regalo.Stato IN ('In_Attesa', 'Rifiutata')
        	    ORDER BY Prodotto.ID_Prodotto DESC
        	    LIMIT 30
        	""";


        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                CardEntity card = new CardEntity(
                    rs.getString("Nome"),//prende valore colonna
                    rs.getBytes("Immagine"),
                    rs.getString("Consegna"),
                    rs.getString("Descrizione"),
                    rs.getString("Nome_Categoria"),
                    rs.getString("Tipo"),
                    rs.getInt("prodotto_id"),
                    rs.getString("Username")
                );
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public List<CardEntity> getFilteredCards(List<String> categorie, List<String> tipologie) {
        List<CardEntity> cards = new ArrayList<>();

        StringBuilder query = new StringBuilder("""
            SELECT Prodotto.ID_Prodotto,
                   Prodotto.Nome,
                   Prodotto.Descrizione,
                   Prodotto.Immagine,
                   COALESCE(Annuncio_Vendita.Consegna, Annuncio_Regalo.Consegna) AS Consegna,
                   Categoria.Nome_Categoria,
                   CASE
                       WHEN Annuncio_Vendita.ID_Prodotto IS NOT NULL THEN 'Vendita'
                       WHEN Annuncio_Scambio.ID_Prodotto IS NOT NULL THEN 'Scambio'
                       WHEN Annuncio_Regalo.ID_Prodotto IS NOT NULL THEN 'Regalo'
                       ELSE 'Nessuno'
                   END AS TipoAnnuncio,
                   Prodotto.Username
            FROM Prodotto
            JOIN Categoria ON Prodotto.ID_Categoria = Categoria.ID_Categoria
            LEFT JOIN Annuncio_Vendita ON Annuncio_Vendita.ID_Prodotto = Prodotto.ID_Prodotto
            LEFT JOIN Annuncio_Scambio ON Annuncio_Scambio.ID_Prodotto = Prodotto.ID_Prodotto
            LEFT JOIN Annuncio_Regalo ON Annuncio_Regalo.ID_Prodotto = Prodotto.ID_Prodotto
            WHERE 1=1
        """);

        if (categorie != null && !categorie.isEmpty()) {
            String placeholders = categorie.stream().map(s -> "?").collect(Collectors.joining(", "));
            query.append(" AND Categoria.Nome_Categoria IN (").append(placeholders).append(")");
        }

        if (tipologie != null && !tipologie.isEmpty()) {
            List<String> conditions = new ArrayList<>();
            if (tipologie.contains("Vendita")) conditions.add("Annuncio_Vendita.ID_Prodotto IS NOT NULL");
            if (tipologie.contains("Scambio")) conditions.add("Annuncio_Scambio.ID_Prodotto IS NOT NULL");
            if (tipologie.contains("Regalo")) conditions.add("Annuncio_Regalo.ID_Prodotto IS NOT NULL");

            if (!conditions.isEmpty()) {
                query.append(" AND (").append(String.join(" OR ", conditions)).append(")");
            }
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (categorie != null && !categorie.isEmpty()) {
                for (String cat : categorie) {
                    stmt.setString(index++, cat);
                }
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CardEntity card = new CardEntity(
                    rs.getString("Nome"),
                    rs.getBytes("Immagine"),
                    rs.getString("Consegna"),
                    rs.getString("Descrizione"),
                    rs.getString("Nome_Categoria"),
                    rs.getString("TipoAnnuncio"),
                    rs.getInt("ID_Prodotto"),
                    rs.getString("Username")
                );
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

}
