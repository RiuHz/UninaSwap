package DTO;

import aggiungiProdotto.AggiungiProdottoDAOPostgre;
import entity.Prodotto;
import exceptionInserimento.*;

public class AggiungiProdottoDTO {

    private final Prodotto prodotto;

    public AggiungiProdottoDTO(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void validaEInserisci() throws
            NomeProdottoNonValidoException,
            DescrizioneNonValidaException,
            ImmagineNonValidaException,
            CategoriaNonValidaException,
            ConsegnaNonValidaException,
            PrezzoNonValidoException {

        // Validazione nome
        if (prodotto.getNome() == null || prodotto.getNome().length() <= 5) {
            throw new NomeProdottoNonValidoException("Il nome deve avere più di 5 caratteri.");
        }

        // Validazione descrizione
        if (prodotto.getDescrizione() == null || prodotto.getDescrizione().length() <= 15) {
            throw new DescrizioneNonValidaException("La descrizione deve avere più di 15 caratteri.");
        }

        // Validazione immagine
        if (prodotto.getImmagine() == null || prodotto.getImmagine().length == 0) {
            throw new ImmagineNonValidaException("L'immagine è obbligatoria.");
        }

        // Validazione categoria
        if (prodotto.getIdCategoria() <= 0) {
            throw new CategoriaNonValidaException("Categoria non valida.");
        }

        // Validazione campi annuncio
        String tipo = prodotto.getTipoAnnuncio();
        if (tipo != null) {
            // Validazione consegna
            if (prodotto.getConsegna() == null || prodotto.getConsegna().length() <= 15) {
                throw new ConsegnaNonValidaException("La consegna deve avere più di 15 caratteri.");
            }

            // Validazione prezzo per Annuncio_Vendita
            if (tipo.equalsIgnoreCase("Vendita")) {
                Double prezzo = prodotto.getPrezzo();
                if (prezzo == null || prezzo <= 0) {
                    throw new PrezzoNonValidoException("Il prezzo deve essere maggiore di zero.");
                }
            }
        }

        // Se tutto è valido, invia alla DAO
        new AggiungiProdottoDAOPostgre().inserisciProdotto(prodotto);
    }
}
