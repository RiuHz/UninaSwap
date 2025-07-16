package dto.proposte;

import dto.annunci.AnnuncioRegaloDTO;
import exception.MessageNotFoundException;

public class PropostaRegaloDTO extends PropostaDTO {

	private String messaggio;
	public AnnuncioRegaloDTO annuncio;
	
	public PropostaRegaloDTO(String stato, String messaggio, String username, AnnuncioRegaloDTO annuncio) {
		super(stato, username);
		this.messaggio = messaggio;
		this.annuncio = annuncio;
	}
	
	public String getMessaggio() throws MessageNotFoundException {
		if (messaggio == null)
			throw new MessageNotFoundException();
		
		return messaggio;
	}
	
}
