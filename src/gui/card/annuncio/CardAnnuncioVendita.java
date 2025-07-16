package gui.card.annuncio;

import javax.swing.JButton;

import controller.AppController;
import dto.annunci.AnnuncioVenditaDTO;

public class CardAnnuncioVendita extends CardAnnuncio {


	private static final long serialVersionUID = 1L;

	public CardAnnuncioVendita(AppController controller, AnnuncioVenditaDTO annuncio) {
		super(annuncio);
	}

	@Override
	protected JButton getButtonAnnuncio() {
		JButton bottone = new OutlinedButton("Compra oggetto", black);
		
		// TODO Apre il dialog per la vendita
		
		return bottone;
	}

}
