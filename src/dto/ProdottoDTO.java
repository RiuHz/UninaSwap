package dto;

public class ProdottoDTO {

	private int id;
    private String nome;
    private String descrizione;
    private byte[] immagine;
    private CategoriaDTO categoria;
    private UtenteDTO utente;

    public ProdottoDTO(int id, String nome, String descrizione, byte[] immagine, CategoriaDTO categoria, UtenteDTO utente) {
        this.id = id;
    	this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.categoria = categoria;
        this.utente = utente;
    }
    
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
    
    public CategoriaDTO getCategoria() {
    	return categoria;
    }
    
    public UtenteDTO getUtente() {
    	return utente;
    }
    
    @Override
    public String toString() {
    	return nome;
    }
}
