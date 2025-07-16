package gui.card.proposta.ricevuta;

import javax.swing.JButton;
import javax.swing.JLabel;

import controller.AppController;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaVenditaRicevuta extends CardProposta {
	
	private static final long serialVersionUID = 1L;
	
	private PropostaVenditaDTO proposta;
	
	public CardPropostaVenditaRicevuta(AppController controller, PropostaVenditaDTO proposta) {
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
		JLabel prezzo = createLabel("Prezzo originale : €" + proposta.annuncio.getPrezzo() + " - Prezzo proposto : €" + proposta.getProposta()); 
		
		JButton bottoneInterazione = getBottoneInterazione();
		JButton bottoneRifiuta = getBottoneRifiuta();
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, utenteProposta, prezzo);
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

}
