package dto;

public class ProdottoDTO {

	private int id;
    private String nome;
    private String descrizione;
    private byte[] immagine;
    public CategoriaDTO categoria;
    private String username;

    public ProdottoDTO(int id, String nome, String descrizione, byte[] immagine, CategoriaDTO categoria, String username) {
        this.id = id;
    	this.nome = nome;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.categoria = categoria;
        this.username = username;
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
    
    public String getUser() {
    	return username;
    }
    
    public String getNomeCategoria() {
    	return categoria.getNome();
    }
    
    public int getIdCategoria() {
    	return categoria.getId();
    }
    
}
