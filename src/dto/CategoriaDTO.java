package dto;

public class CategoriaDTO {

	private int id;
    private String nome;

    public CategoriaDTO(int id, String nome) {
        this.id = id;
    	this.nome = nome;
    }

    public int getId() {
    	return id;
    }
    
    public String getNome() {
        return nome;
    }

}
