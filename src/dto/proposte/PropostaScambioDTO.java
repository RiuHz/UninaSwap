package dto.proposte;

import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.annunci.AnnuncioScambioDTO;

public class PropostaScambioDTO extends PropostaDTO {

	private int id;
	public AnnuncioScambioDTO annuncio;
	public ArrayList<ProdottoDTO> listaProdottiScambiati;
	
	public PropostaScambioDTO(int id, String stato, String username, AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> listaProdottiScambiati) {
		super(stato, username);
		this.id = id;
		this.annuncio = annuncio;
		this.listaProdottiScambiati = listaProdottiScambiati;
	}
	
	public int getId() {
		return id;
	}
	
}
