package gui.card.proposta.ricevuta;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.AppController;
import dto.proposte.PropostaRegaloDTO;
import exception.MessageNotFoundException;
import gui.card.proposta.CardProposta;

public class CardPropostaRegaloRicevuta extends CardProposta {
	
	private static final long serialVersionUID = 1L;
	
	private PropostaRegaloDTO proposta;

	public CardPropostaRegaloRicevuta(AppController controller, PropostaRegaloDTO proposta) {
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
		JTextArea messaggioLasciato = createTextArea(getMessaggioProposta());
		
		JButton bottoneInterazione = getBottoneInterazione();
		JButton bottoneRifiuta = getBottoneRifiuta();
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, utenteProposta, messaggioLasciato);
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
	
	private String getMessaggioProposta() {
		String messaggio = "";
		
		try {
			messaggio = "Messaggio : " + proposta.getMessaggio();
		} catch (MessageNotFoundException error) {
			messaggio = "L'utente non ha lasciato nessun messaggio";
		}
		
		return messaggio;
	}

}
