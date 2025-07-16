package dto;

public class UniversitaDTO {
	
	private int id;
	private String nome;
	
	public UniversitaDTO(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	int getId() {
		return id;
	}
	
	String getNome() {
		return nome;
	}

}
