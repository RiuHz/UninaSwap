package dao.aggiungiProdotto;

import entity.Prodotto;
import exception.inserimento.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import daoNew.DatabaseManager;

public class AggiungiProdottoDAOPostgre {

    public void inserisciProdotto(Prodotto prodotto) throws
            NomeProdottoNonValidoException,
            DescrizioneNonValidaException,
            ImmagineNonValidaException,
            CategoriaNonValidaException,
            ConsegnaNonValidaException,
            PrezzoNonValidoException {

        String insertProdotto = """
            INSERT INTO Prodotto (Nome, Immagine, Descrizione, Username, ID_Categoria)
            VALUES (?, ?, ?, ?, ?)
            RETURNING ID_Prodotto
        """;

        String insertAnnuncioVendita = """
            INSERT INTO Annuncio_Vendita (Stato, Consegna, Prezzo, ID_Prodotto)
            VALUES ('Attivo', ?, ?, ?)
        """;

        String insertAnnuncioScambio = """
            INSERT INTO Annuncio_Scambio (Stato, Consegna, ID_Prodotto)
            VALUES ('Attivo', ?, ?)
        """;

        String insertAnnuncioRegalo = """
            INSERT INTO Annuncio_Regalo (Stato, Consegna, ID_Prodotto)
            VALUES ('Attivo', ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            int idProdotto;

            try (PreparedStatement stmt = conn.prepareStatement(insertProdotto)) {
                stmt.setString(1, prodotto.getNome());
                stmt.setBytes(2, prodotto.getImmagine());
                stmt.setString(3, prodotto.getDescrizione());
                stmt.setString(4, prodotto.getUsername());
                stmt.setInt(5, prodotto.getIdCategoria());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idProdotto = rs.getInt("ID_Prodotto");
                } else {
                    conn.rollback();
                    throw new SQLException("Impossibile ottenere ID_Prodotto");
                }
            }

            if (prodotto.getTipoAnnuncio() != null) {
                String tipo = prodotto.getTipoAnnuncio();

                switch (tipo) {
                    case "Vendita" -> {
                        if (prodotto.getPrezzo() == null || prodotto.getPrezzo() <= 0)
                            throw new PrezzoNonValidoException("Il prezzo deve essere maggiore di 0.");

                        try (PreparedStatement stmt = conn.prepareStatement(insertAnnuncioVendita)) {
                            stmt.setString(1, prodotto.getConsegna());
                            stmt.setDouble(2, prodotto.getPrezzo());
                            stmt.setInt(3, idProdotto);
                            stmt.executeUpdate();
                        }
                    }

                    case "Scambio", "Regalo" -> {
                        if (prodotto.getConsegna() == null || prodotto.getConsegna().isBlank())
                            throw new ConsegnaNonValidaException("La modalità di consegna non può essere vuota.");

                        try (PreparedStatement stmt = conn.prepareStatement(
                                tipo.equals("Scambio") ? insertAnnuncioScambio : insertAnnuncioRegalo)) {
                            stmt.setString(1, prodotto.getConsegna());
                            stmt.setInt(2, idProdotto);
                            stmt.executeUpdate();
                        }
                    }

                    default -> {
                        conn.rollback();
                        throw new IllegalArgumentException("Tipo di annuncio non valido: " + tipo);
                    }
                }
            }

            conn.commit();

        } catch (SQLException e) {
            String msg = e.getMessage().toLowerCase();

            if (msg.contains("nome"))
                throw new NomeProdottoNonValidoException("Errore SQL sul nome: " + e.getMessage());
            if (msg.contains("descrizione"))
                throw new DescrizioneNonValidaException("Errore SQL sulla descrizione: " + e.getMessage());
            if (msg.contains("immagine"))
                throw new ImmagineNonValidaException("Errore SQL sull'immagine: " + e.getMessage());
            if (msg.contains("categoria"))
                throw new CategoriaNonValidaException("Errore SQL sulla categoria: " + e.getMessage());
            if (msg.contains("consegna"))
                throw new ConsegnaNonValidaException("Errore SQL sulla consegna: " + e.getMessage());
            if (msg.contains("prezzo"))
                throw new PrezzoNonValidoException("Errore SQL sul prezzo: " + e.getMessage());

            throw new RuntimeException("Errore generico nel database: " + e.getMessage(), e);
        }
    }
}
