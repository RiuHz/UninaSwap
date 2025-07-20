package gui.card.proposta.ricevuta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.proposte.PropostaRegaloDTO;
import exception.MessageNotFoundException;
import gui.card.proposta.CardProposta;

public class CardPropostaRegaloRicevuta extends CardProposta {
	
	private static final long serialVersionUID = 1L;

	public CardPropostaRegaloRicevuta(ControllerApp controller, PropostaRegaloDTO proposta) {
		
		creaComponenti(controller, proposta);
		
	}
	
	private void creaComponenti(ControllerApp controller, PropostaRegaloDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto());
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel utenteProposta = creaLabel("Proposta ricevuta da : " + proposta.getUtente());
		JTextArea messaggioLasciato = creaTextArea(getMessaggioProposta(proposta));
		
		JButton bottoneInterazione = getBottoneAccetta(controller, proposta);
		JButton bottoneRifiuta = getBottoneRifiuta(controller, proposta);
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, utenteProposta, messaggioLasciato);
	}
	
	private JButton getBottoneAccetta(ControllerApp controller, PropostaRegaloDTO proposta) {
		JButton bottoneAccetta = new OutlinedButton("Accetta", verde);
		
		bottoneAccetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneAccettaClick(controller, proposta);
			}
		});
		
		return bottoneAccetta;
	}
	
	public void bottoneAccettaClick(ControllerApp controller, PropostaRegaloDTO proposta) {
		try {
			controller.accettaPropostaRegalo(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
	}

	private JButton getBottoneRifiuta(ControllerApp controller, PropostaRegaloDTO proposta) {
		JButton bottoneRifiuta = new OutlinedButton("Rifiuta", rosso);
		
		bottoneRifiuta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRifiutaClick(controller, proposta);
			}
		});
		
		return bottoneRifiuta;
	}
	
	public void bottoneRifiutaClick(ControllerApp controller, PropostaRegaloDTO proposta) {
		try {
			controller.rifiutaPropostaRegalo(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
	}
	
	private String getMessaggioProposta(PropostaRegaloDTO proposta) {
		String messaggio = "";
		
		try {
			messaggio = "Messaggio : " + proposta.getMessaggio();
		} catch (MessageNotFoundException error) {
			messaggio = "L'utente non ha lasciato nessun messaggio";
		}
		
		return messaggio;
	}

}
