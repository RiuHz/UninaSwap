package dto.proposte;

import dto.UtenteDTO;
import dto.annunci.AnnuncioVenditaDTO;

public class PropostaVenditaDTO extends PropostaDTO {

	private double proposta;
	private AnnuncioVenditaDTO annuncio;
	
	public PropostaVenditaDTO(double proposta, String stato, UtenteDTO utente, AnnuncioVenditaDTO annuncio) {
		super(stato, utente);
		this.proposta = proposta;
		this.annuncio = annuncio;
	}
	
	public double getProposta() {
		return proposta;
	}
	
	public AnnuncioVenditaDTO getAnnuncio() {
		return annuncio;
	}
	
}
