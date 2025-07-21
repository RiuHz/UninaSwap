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
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.proposte.PropostaRegaloDTO;
import exception.MessageNotFoundException;
import gui.card.proposta.CardProposta;
import gui.dialog.FinestraDialog;
import gui.dialog.proposta.DialogModificaPropostaRegalo;

public class CardPropostaRegaloInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private FinestraDialog DialogModificaPropostaRegalo;
	
	public CardPropostaRegaloInviata(JFrame finestra, ControllerApp controller, PropostaRegaloDTO proposta) {
		DialogModificaPropostaRegalo = new DialogModificaPropostaRegalo(finestra, controller, proposta);
		
		createComponents(controller, proposta);	
	}
	
	private void createComponents(ControllerApp controller, PropostaRegaloDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto());
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome() + " di " + proposta.getAnnuncio().getProdotto().getUtente());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel stato = getStato(proposta);
		JTextArea messaggioLasciato = creaTextArea(getMessaggioProposta(proposta)); 
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneModifica(controller, proposta);
			bottoneRifiuta = getBottoneRitira(controller, proposta);
		} else {
			bottoneInterazione = creaBottoneGrigio("Modifica");
			bottoneRifiuta = creaBottoneGrigio("Ritira");
		}
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, stato, messaggioLasciato);
	}
	
	private JButton getBottoneModifica(ControllerApp controller, PropostaRegaloDTO proposta) {
		JButton bottoneModifica = new OutlinedButton("Modifica", arancione);
		
		bottoneModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogModificaPropostaRegalo.mostraDialog();
			}
		});
		
		return bottoneModifica;
	}

	private JButton getBottoneRitira(ControllerApp controller, PropostaRegaloDTO proposta) {
		JButton bottoneRitira = new OutlinedButton("Ritira", rosso);
		
		bottoneRitira.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRitiraClick(controller, proposta);
			}
		});
		
		return bottoneRitira;
	}
	
	private void bottoneRitiraClick(ControllerApp controller, PropostaRegaloDTO proposta) {
		try {
			controller.ritiraPropostaRegalo(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String getMessaggioProposta(PropostaRegaloDTO proposta) {
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
	
	private JButton creaBottoneGrigio(String testo) {
		JButton bottone = new OutlinedButton(testo, Color.gray);
		
		bottone.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
		return bottone;
	}
	
}
