package dao.category;

import db.DatabaseManager;
import entity.CardEntity;
import entity.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDAOPostgre implements CategoryDAOInterface {

    @Override
    public List<Categoria> getAllCategorie() {
        List<Categoria> categorie = new ArrayList<>();

        String query = "SELECT ID_Categoria, Nome_Categoria FROM Categoria ORDER BY Nome_Categoria";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_Categoria");
                String nome = rs.getString("Nome_Categoria");
                categorie.add(new Categoria(id, nome));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorie;
    }

    public List<String> getAllCategoryNames() {
        List<String> nomi = new ArrayList<>();

        String query = "SELECT Nome_Categoria FROM Categoria ORDER BY Nome_Categoria";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                nomi.add(rs.getString("Nome_Categoria"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomi;
    }

    public List<CardEntity> getFilteredCards(List<String> categorie, List<String> tipologie) {
        List<CardEntity> cards = new ArrayList<>();

        StringBuilder query = new StringBuilder("""
            SELECT p.ID_Prodotto, p.Nome, p.Descrizione, p.Immagine, p.Consegna,
                   c.Nome_Categoria,
                   CASE
                       WHEN av.ID_Prodotto IS NOT NULL THEN 'Vendita'
                       WHEN asca.ID_Prodotto IS NOT NULL THEN 'Scambio'
                       WHEN ar.ID_Prodotto IS NOT NULL THEN 'Regalo'
                       ELSE 'Nessuno'
                   END AS TipoAnnuncio,
                   p.Username
            FROM Prodotto p
            JOIN Categoria c ON p.ID_Categoria = c.ID_Categoria
            LEFT JOIN Annuncio_Vendita av ON av.ID_Prodotto = p.ID_Prodotto
            LEFT JOIN Annuncio_Scambio asca ON asca.ID_Prodotto = p.ID_Prodotto
            LEFT JOIN Annuncio_Regalo ar ON ar.ID_Prodotto = p.ID_Prodotto
            WHERE 1=1
        """);

        if (categorie != null && !categorie.isEmpty()) {
            String placeholders = categorie.stream().map(s -> "?").collect(Collectors.joining(", "));
            query.append(" AND c.Nome_Categoria IN (").append(placeholders).append(")");
        }

        if (tipologie != null && !tipologie.isEmpty()) {
            query.append(" AND (");
            List<String> conditions = new ArrayList<>();
            if (tipologie.contains("Vendita")) conditions.add("av.ID_Prodotto IS NOT NULL");
            if (tipologie.contains("Scambio")) conditions.add("asca.ID_Prodotto IS NOT NULL");
            if (tipologie.contains("Regalo")) conditions.add("ar.ID_Prodotto IS NOT NULL");
            query.append(String.join(" OR ", conditions)).append(")");
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
