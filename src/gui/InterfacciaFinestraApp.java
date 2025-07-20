package gui;

import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.annunci.AnnuncioDTO;
import dto.proposte.PropostaDTO;

public interface InterfacciaFinestraApp extends InterfacciaFinestraAccesso {
	
	public void aggiornaAnnunci(ArrayList<AnnuncioDTO> listaAnnunci);
	
	public void aggiornaInventario(ArrayList<ProdottoDTO> listaProdotti);
	
	public void aggiornaProposteRicevute(ArrayList<PropostaDTO> listaProposteRicevute);
	
	public void aggiornaProposteInviate(ArrayList<PropostaDTO> listaProposteInviate);
	
}
