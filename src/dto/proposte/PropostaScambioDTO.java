package dto.proposte;

import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioScambioDTO;

public class PropostaScambioDTO extends PropostaDTO {

	private int id;
	private AnnuncioScambioDTO annuncio;
	private ArrayList<ProdottoDTO> listaProdottiScambiati;
	
	public PropostaScambioDTO(int id, String stato, UtenteDTO utente, AnnuncioScambioDTO annuncio, ArrayList<ProdottoDTO> listaProdottiScambiati) {
		super(stato, utente);
		this.id = id;
		this.annuncio = annuncio;
		this.listaProdottiScambiati = listaProdottiScambiati;
	}
	
	public int getId() {
		return id;
	}
	
	public AnnuncioScambioDTO getAnnuncio() {
		return annuncio;
	}
	
	public ArrayList<ProdottoDTO> getListaProdotti() {
		return listaProdottiScambiati;
	}
	
}
