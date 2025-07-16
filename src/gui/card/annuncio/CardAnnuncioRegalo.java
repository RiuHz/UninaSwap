package gui.card.annuncio;

import javax.swing.JButton;

import controller.AppController;
import dto.annunci.AnnuncioRegaloDTO;

public class CardAnnuncioRegalo extends CardAnnuncio {

	private static final long serialVersionUID = 1L;

	public CardAnnuncioRegalo(AppController controller, AnnuncioRegaloDTO annuncio) {
		super(annuncio);
	}

	@Override
	protected JButton getButtonAnnuncio() {
		JButton bottone = new OutlinedButton("Richiedi Regalo", black);
		
		// TODO apre il dialog per il messaggio
		
		return bottone;
	}

}
