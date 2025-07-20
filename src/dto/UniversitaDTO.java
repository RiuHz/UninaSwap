package dto;

public class UniversitaDTO {
	
	private int id;
	private String nome;
	
	public UniversitaDTO(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}

}
