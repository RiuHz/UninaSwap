package dto.proposte;

import dto.UtenteDTO;

public abstract class PropostaDTO {
	private String stato;
	private UtenteDTO utente;
	
	protected PropostaDTO(String stato, UtenteDTO utente) {
		this.stato = stato;
		this.utente = utente;
	}
	
	public String getStato() {
		return stato;
	}
	
	public UtenteDTO getUtente() {
		return utente;
	}
	
}
