package gui.card.annuncio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.ControllerApp;
import dto.annunci.AnnuncioScambioDTO;
import gui.dialog.FinestraDialog;
import gui.dialog.richiesta.DialogRichiediScambio;

public class CardAnnuncioScambio extends CardAnnuncio {

	private static final long serialVersionUID = 1L;
	
	private FinestraDialog DialogRichiediScambio;

	public CardAnnuncioScambio(JFrame framePadre, ControllerApp controller, AnnuncioScambioDTO annuncio) {
		super(controller, annuncio);
		
		DialogRichiediScambio = new DialogRichiediScambio(framePadre, controller, annuncio);
	}

	@Override
	protected JButton getBottoneAnnuncio(ControllerApp controller) {
		JButton bottone = new OutlinedButton("Richiedi Scambio", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogRichiediScambio.mostraDialog();
			}
		});
		
		return bottone;
	}

}
