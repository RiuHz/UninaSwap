package dao.product;

import db.DatabaseManager;
import entity.Prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPostgre implements ProductDAOInterface {

    @Override
    public List<Prodotto> getProdottiLiberiByUsername(String username) {
        List<Prodotto> prodottiLiberi = new ArrayList<>();

        String query = """
            SELECT p.ID_Prodotto, p.Nome, p.Descrizione
            FROM Prodotto p
            LEFT JOIN Proposta_Scambio ps ON p.ID_Prodotto = ps.ID_Prodotto
            WHERE p.Username = ? AND p.ID_Prop_Scamb IS NULL
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Prodotto prodotto = new Prodotto(
                    rs.getInt("ID_Prodotto"),
                    rs.getString("Nome"),
                    rs.getString("Descrizione")
                );
                prodottiLiberi.add(prodotto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prodottiLiberi;
    }
}
