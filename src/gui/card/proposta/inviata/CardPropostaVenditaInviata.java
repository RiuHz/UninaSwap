package gui.card.proposta.inviata;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JLabel;

import controller.AppController;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaVenditaInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private PropostaVenditaDTO proposta;
	
	public CardPropostaVenditaInviata(AppController controller, PropostaVenditaDTO proposta) {
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
		JLabel stato = getStato(proposta);
		JLabel prezzo = createLabel("Prezzo originale : €" + proposta.annuncio.getPrezzo() + " - Prezzo proposto : €" + proposta.getProposta());
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneInterazione();
			bottoneRifiuta = getBottoneRifiuta();
		} else {
			bottoneInterazione = createGrayedOutButton("Modifica");
			bottoneRifiuta = createGrayedOutButton("Ritira");
		}
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, stato, prezzo);
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
