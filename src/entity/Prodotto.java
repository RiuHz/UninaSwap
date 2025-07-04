package entity;

public class Prodotto {

    private final int id;
    private final String nome;
    private final String descrizione;
    private final byte[] immagine;
    private final int idCategoria;
    private final String username;

    // Campi specifici per Annunci
    private final String tipoAnnuncio; // "Vendita", "Scambio", "Regalo", oppure null
    private final String consegna;
    private final Double prezzo; // solo per Annuncio di Vendita

    //Costruttore per la dialog di proposta scambio
    public Prodotto(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = null;
        this.username = null;
        this.idCategoria = (Integer) null;
        this.tipoAnnuncio = null;
        this.consegna = null;
        this.prezzo = null;
    }


    // Costruttore per Prodotto semplice (senza annuncio)
    public Prodotto(String nome, String descrizione, byte[] immagine, String username, int idCategoria) {
        this.id = -1;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.username = username;
        this.idCategoria = idCategoria;
        this.tipoAnnuncio = null;
        this.consegna = null;
        this.prezzo = null;
    }

    // Costruttore per Annuncio Regalo / Scambio
    public Prodotto(String nome, String descrizione, byte[] immagine, String username, int idCategoria,
                    String tipoAnnuncio, String consegna) {
        this.id = -1;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.username = username;
        this.idCategoria = idCategoria;
        this.tipoAnnuncio = tipoAnnuncio;
        this.consegna = consegna;
        this.prezzo = null;
    }

    // Costruttore per Annuncio Vendita
    public Prodotto(String nome, String descrizione, byte[] immagine, String username, int idCategoria,
                    String tipoAnnuncio, String consegna, Double prezzo) {
        this.id = -1;
        this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.username = username;
        this.idCategoria = idCategoria;
        this.tipoAnnuncio = tipoAnnuncio;
        this.consegna = consegna;
        this.prezzo = prezzo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getUsername() {
        return username;
    }

    public String getTipoAnnuncio() {
        return tipoAnnuncio;
    }

    public String getConsegna() {
        return consegna;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    @Override
    public String toString() {
        return nome + " - " + descrizione;
    }
}
