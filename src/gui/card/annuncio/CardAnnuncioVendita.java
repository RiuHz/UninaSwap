package gui.card.annuncio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.annunci.AnnuncioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import gui.dialog.richiesta.DialogRichiediVendita;

public class CardAnnuncioVendita extends CardAnnuncio {

	private static final long serialVersionUID = 1L;
	
	private DialogRichiediVendita DialogRichiediVendita;

	public CardAnnuncioVendita(JFrame finestra, ControllerApp controller, AnnuncioVenditaDTO annuncio) {
		super(controller, annuncio);
		
		DialogRichiediVendita = new DialogRichiediVendita(finestra, controller, annuncio);
	}
	
	@Override
	protected void creaComponenti(ControllerApp controller, AnnuncioDTO annuncio) {
		JLabel immagine = getImmagineRidimensionata(annuncio.getProdotto());
		
		JLabel titolo = creaLabel(annuncio.getProdotto().getNome() + " di " + annuncio.getProdotto().getUtente());
		JLabel categoria = creaLabel("Categoria : " + annuncio.getProdotto().getCategoria());
		JLabel prezzo = creaLabel("Prezzo : " + ((AnnuncioVenditaDTO) annuncio).getPrezzo());
		JTextArea descrizione = creaTextArea("Descrizione : " + annuncio.getProdotto().getDescrizione());
		JTextArea consegna = creaTextArea("Consegna : " + annuncio.getConsegna());
		JLabel data = creaLabel("Pubblicato : " + annuncio.getData());
		
		JButton bottone = getBottoneAnnuncio(controller);
		
		aggiungiAlLayout(immagine, bottone, titolo, categoria, prezzo, descrizione, consegna, data);
	}

	@Override
	protected JButton getBottoneAnnuncio(ControllerApp controller) {
		JButton bottone = new OutlinedButton("Compra oggetto", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogRichiediVendita.mostraDialog();
			}
		});
		
		return bottone;
	}

}
