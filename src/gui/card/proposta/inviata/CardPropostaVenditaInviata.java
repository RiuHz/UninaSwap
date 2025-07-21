package gui.card.proposta.inviata;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.ControllerApp;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;
import gui.dialog.FinestraDialog;
import gui.dialog.proposta.DialogModificaPropostaVendita;

public class CardPropostaVenditaInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private FinestraDialog DialogModificaPropostaVendita;
	
	public CardPropostaVenditaInviata(JFrame finestra, ControllerApp controller, PropostaVenditaDTO proposta) {
		DialogModificaPropostaVendita = new DialogModificaPropostaVendita(finestra, controller, proposta);
		
		creaComponenti(controller, proposta);
	}
	
	private void creaComponenti(ControllerApp controller, PropostaVenditaDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto());
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome() + " di " + proposta.getAnnuncio().getProdotto().getUtente());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel stato = getStato(proposta);
		JLabel prezzo = creaLabel("Prezzo originale : €" + proposta.getAnnuncio().getPrezzo() + " - Prezzo proposto : €" + proposta.getProposta());
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneModifica(controller, proposta);
			bottoneRifiuta = getBottoneRitira(controller, proposta);
		} else {
			bottoneInterazione = creaBottoneGrigio("Modifica");
			bottoneRifiuta = creaBottoneGrigio("Ritira");
		}
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, stato, prezzo);
	}
	
	private JButton getBottoneModifica(ControllerApp controller, PropostaVenditaDTO proposta) {
		JButton bottoneModifica = new OutlinedButton("Modifica", arancione);
		
		bottoneModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogModificaPropostaVendita.mostraDialog();
			}
		});
		
		return bottoneModifica;
	}

	private JButton getBottoneRitira(ControllerApp controller, PropostaVenditaDTO proposta) {
		JButton bottoneRitira = new OutlinedButton("Ritira", rosso);
		
		bottoneRitira.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRitiraClick(controller, proposta);
			}
		});
		
		
		return bottoneRitira;
	}
	
	private void bottoneRitiraClick(ControllerApp controller, PropostaVenditaDTO proposta) {
		try {
			controller.ritiraPropostaVendita(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
	private JButton creaBottoneGrigio(String testo) {
		JButton bottone = new OutlinedButton(testo, Color.gray);
		
		bottone.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		return bottone;
	}

}
