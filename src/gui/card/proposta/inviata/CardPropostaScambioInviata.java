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
import dto.ProdottoDTO;
import dto.proposte.PropostaScambioDTO;
import gui.card.proposta.CardProposta;
import gui.dialog.FinestraDialog;
import gui.dialog.proposta.DialogModificaPropostaScambio;

public class CardPropostaScambioInviata extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	private FinestraDialog DialogModificaPropostaScambio;
	
	public CardPropostaScambioInviata(JFrame finestra, ControllerApp controller, PropostaScambioDTO proposta) {
		DialogModificaPropostaScambio = new DialogModificaPropostaScambio(finestra, controller, proposta);
		
		creaComponenti(controller, proposta);
	}
	
	private void creaComponenti(ControllerApp controller, PropostaScambioDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto());
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome() + " di " + proposta.getAnnuncio().getProdotto().getUtente());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel stato = getStato(proposta);
		JTextArea oggettiProposti = creaTextArea(getOggettiProposti(proposta)); 
		
		JButton bottoneInterazione, bottoneRifiuta;
		
		if (proposta.getStato().equals("In Attesa")) {
			bottoneInterazione = getBottoneModifica(controller, proposta);
			bottoneRifiuta = getBottoneRitira(controller, proposta);
		} else {
			bottoneInterazione = creaBottoneGrigio("Modifica");
			bottoneRifiuta = creaBottoneGrigio("Ritira");
		}
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, stato, oggettiProposti);
	}
	
	private JButton getBottoneModifica(ControllerApp controller, PropostaScambioDTO proposta) {
		JButton bottoneModifica = new OutlinedButton("Modifica", arancione);
		
		bottoneModifica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogModificaPropostaScambio.mostraDialog();
			}
		});
		
		return bottoneModifica;
	}

	private JButton getBottoneRitira(ControllerApp controller, PropostaScambioDTO proposta) {
		JButton bottoneRitira = new OutlinedButton("Ritira", rosso);
		
		bottoneRitira.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRitiraClick(controller, proposta);
			}
		});
		
		return bottoneRitira;
	}
	
	private void bottoneRitiraClick(ControllerApp controller, PropostaScambioDTO proposta) {
		try {
			controller.ritiraPropostaScambio(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private String getOggettiProposti(PropostaScambioDTO proposta) {
		String oggetti = "Gli oggetti proposti sono : ";
		
		for (ProdottoDTO prodotto : proposta.getListaProdotti())
			oggetti += prodotto.getNome() + ", ";
		
		return oggetti.substring(0, oggetti.length() - 2);
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
