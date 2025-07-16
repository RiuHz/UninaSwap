package gui.card.proposta.ricevuta;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.AppController;
import dto.ProdottoDTO;
import dto.proposte.PropostaScambioDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaScambioRicevuta extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private PropostaScambioDTO proposta;
	
	public CardPropostaScambioRicevuta(AppController controller, PropostaScambioDTO proposta) {
		this.proposta = proposta;
		
		createComponents();
	}
	
	@Override
	public int getIdProdotto() {
		return proposta.annuncio.prodotto.getId();
	}
	
	private void createComponents() {
		JLabel image = getResizedImage(); // TODO Bisogna prenderlo dal DB, dio porco
		
		JLabel titolo = createLabel(proposta.annuncio.prodotto.getNome());
		JLabel utenteProposta = createLabel("Proposta ricevuta da : " + proposta.getUser());
		JTextArea oggettiProposti = createTextArea(getOggettiProposti()); 
		
		JButton bottoneInterazione = getBottoneInterazione();
		JButton bottoneRifiuta = getBottoneRifiuta();
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, utenteProposta, oggettiProposti);
	}
	
	private JButton getBottoneInterazione() {
		JButton bottoneAccetta = new OutlinedButton("Accetta", green);
		
		// TODO metodo accetta controller
		
		return bottoneAccetta;
	}

	private JButton getBottoneRifiuta() {
		JButton bottoneRifiuta = new OutlinedButton("Rifiuta", red);
		
		// TODO metodo rifiuta controller
		
		return bottoneRifiuta;
	}
	
	private String getOggettiProposti() {
		String oggetti = "Gli oggetti proposti sono : ";
		
		for (ProdottoDTO prodotto : proposta.listaProdottiScambiati)
			oggetti += prodotto.getNome() + ", ";
		
		return oggetti.substring(0, oggetti.length() - 2);
	}

}
