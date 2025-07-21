package gui.card.annuncio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import controller.ControllerApp;
import dto.annunci.AnnuncioRegaloDTO;
import gui.dialog.FinestraDialog;
import gui.dialog.richiesta.DialogRichiediRegalo;

public class CardAnnuncioRegalo extends CardAnnuncio {

	private static final long serialVersionUID = 1L;
	
	private FinestraDialog DialogRichiediRegalo;

	public CardAnnuncioRegalo(JFrame finestra, ControllerApp controller, AnnuncioRegaloDTO annuncio) {
		super(controller, annuncio);
		
		DialogRichiediRegalo = new DialogRichiediRegalo(finestra, controller, annuncio);
	}

	@Override
	protected JButton getBottoneAnnuncio(ControllerApp controller) {
		JButton bottone = new OutlinedButton("Richiedi Regalo", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogRichiediRegalo.mostraDialog();
			}
		});
		
		return bottone;
	}

}
