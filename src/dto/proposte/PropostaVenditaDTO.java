package dto.proposte;

import dto.annunci.AnnuncioVenditaDTO;

public class PropostaVenditaDTO extends PropostaDTO {

	private double proposta;
	public AnnuncioVenditaDTO annuncio;
	
	public PropostaVenditaDTO(double proposta, String stato, String username, AnnuncioVenditaDTO annuncio) {
		super(stato, username);
		this.proposta = proposta;
		this.annuncio = annuncio;
	}
	
	public double getProposta() {
		return proposta;
	}
	
}
