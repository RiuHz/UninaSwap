package dto.proposte;

import dto.UtenteDTO;
import dto.annunci.AnnuncioRegaloDTO;
import exception.MessageNotFoundException;

public class PropostaRegaloDTO extends PropostaDTO {

	private String messaggio;
	private AnnuncioRegaloDTO annuncio;
	
	public PropostaRegaloDTO(String stato, String messaggio, UtenteDTO utente, AnnuncioRegaloDTO annuncio) {
		super(stato, utente);
		this.messaggio = messaggio;
		this.annuncio = annuncio;
	}
	
	public String getMessaggio() throws MessageNotFoundException {
		if (messaggio == null)
			throw new MessageNotFoundException();
		
		return messaggio;
	}
	
	public AnnuncioRegaloDTO getAnnuncio() {
		return annuncio;
	}
	
}
