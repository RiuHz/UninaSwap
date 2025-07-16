package gui.card.proposta.inviata;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.AppController;
import dto.proposte.PropostaRegaloDTO;
import exception.MessageNotFoundException;
import gui.card.proposta.CardProposta;

public class CardPropostaRegaloInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private PropostaRegaloDTO proposta;
	
	public CardPropostaRegaloInviata(AppController controller, PropostaRegaloDTO proposta) {
		this.proposta = proposta;
		
		createComponents();	
	}
	
	@Override
	public int getIdProdotto() {
		return proposta.annuncio.prodotto.getId();
	}
	
	private void createComponents() {
		JLabel image = getResizedImage(); // TODO Bisogna prenderlo dal DB, dio porco
		
		JLabel titolo = createLabel(proposta.annuncio.prodotto.getNome() + " di " + proposta.annuncio.prodotto.getUser());
		JLabel stato = getStato(proposta);
		JTextArea messaggioLasciato = createTextArea(getMessaggioProposta()); 
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneInterazione();
			bottoneRifiuta = getBottoneRifiuta();
		} else {
			bottoneInterazione = createGrayedOutButton("Modifica");
			bottoneRifiuta = createGrayedOutButton("Ritira");
		}
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, stato, messaggioLasciato);
	}
	
	private JButton getBottoneInterazione() {
		JButton bottoneAccetta = new OutlinedButton("Modifica", orange);
		
		// TODO metodo accetta controller
		
		return bottoneAccetta;
	}

	private JButton getBottoneRifiuta() {
		JButton bottoneRifiuta = new OutlinedButton("Ritira", red);
		
		// TODO metodo rifiuta controller
		
		return bottoneRifiuta;
	}
	
	private String getMessaggioProposta() {
		String messaggio = "";
		
		try {
			messaggio = "Messaggio : " + proposta.getMessaggio();
		} catch (MessageNotFoundException error) {
			messaggio = "Non hai lasciato nessun messaggio";
		}
		
		return  messaggio;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
	private JButton createGrayedOutButton(String text) {
		JButton bottone = new OutlinedButton(text, Color.gray);
		
		bottone.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		return bottone;
	}
	
}
