package controller;

import DTO.AggiungiProdottoDTO;
import entity.Prodotto;
import exceptionInserimento.*;

public class AggiungiProdottoController {

    public void aggiungiProdotto(String nome, String descrizione, byte[] immagine, String username,
                                 int idCategoria, String tipoAnnuncio,
                                 String consegna, Double prezzo)
            throws NomeProdottoNonValidoException,
                   DescrizioneNonValidaException,
                   ImmagineNonValidaException,
                   CategoriaNonValidaException,
                   ConsegnaNonValidaException,
                   PrezzoNonValidoException {

        Prodotto prodotto;

        switch (tipoAnnuncio) {
            case "Vendita" -> prodotto = new Prodotto(nome, descrizione, immagine, username, idCategoria, tipoAnnuncio, consegna, prezzo);
            case "Scambio", "Regalo" -> prodotto = new Prodotto(nome, descrizione, immagine, username, idCategoria, tipoAnnuncio, consegna);
            case "Nessuno" -> prodotto = new Prodotto(nome, descrizione, immagine, username,idCategoria);
            default -> throw new IllegalArgumentException("Tipo di annuncio non valido: " + tipoAnnuncio);
        }

        new AggiungiProdottoDTO(prodotto).validaEInserisci();
    }
}
