package gui.card.annuncio;

import javax.swing.JButton;

import controller.AppController;
import dto.annunci.AnnuncioScambioDTO;

public class CardAnnuncioScambio extends CardAnnuncio {

	private static final long serialVersionUID = 1L;

	public CardAnnuncioScambio(AppController controller, AnnuncioScambioDTO annuncio) {
		super(annuncio);
	}

	@Override
	protected JButton getButtonAnnuncio() {
		JButton bottone = new OutlinedButton("Richiedi Scambio", black);
		
		// TODO apre il dialog per lo scambio
		
		return bottone;
	}

}
