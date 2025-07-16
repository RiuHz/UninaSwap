package dto.proposte;

public abstract class PropostaDTO {
	private String stato;
	private String username;
	
	protected PropostaDTO(String stato, String username) {
		this.stato = stato;
		this.username = username;
	}
	
	public String getStato() {
		return stato;
	}

	public String getUser() {
		return username;
	}

}
