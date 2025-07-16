package gui.card.proposta.inviata;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.AppController;
import dto.ProdottoDTO;
import dto.proposte.PropostaScambioDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaScambioInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private PropostaScambioDTO proposta;
	
	public CardPropostaScambioInviata(AppController controller, PropostaScambioDTO proposta) {
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
		JTextArea oggettiProposti = createTextArea(getOggettiProposti()); 
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneInterazione();
			bottoneRifiuta = getBottoneRifiuta();
		} else {
			bottoneInterazione = createGrayedOutButton("Modifica");
			bottoneRifiuta = createGrayedOutButton("Ritira");
		}
		
		addToLayout(image, bottoneInterazione, bottoneRifiuta, titolo, stato, oggettiProposti);
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
	
	private String getOggettiProposti() {
		String oggetti = "Gli oggetti proposti sono : ";
		
		for (ProdottoDTO prodotto : proposta.listaProdottiScambiati)
			oggetti += prodotto.getNome() + ", ";
		
		return oggetti.substring(0, oggetti.length() - 2);
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
