package gui.card.proposta.ricevuta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.ControllerApp;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaVenditaRicevuta extends CardProposta {
	
	private static final long serialVersionUID = 1L;
	
	public CardPropostaVenditaRicevuta(ControllerApp controller, PropostaVenditaDTO proposta) {
		
		creaComponenti(controller, proposta);
		
	}
	
	private void creaComponenti(ControllerApp controller, PropostaVenditaDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto()); 
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel utenteProposta = creaLabel("Proposta ricevuta da : " + proposta.getUtente());
		JLabel prezzo = creaLabel("Prezzo originale : €" + proposta.getAnnuncio().getPrezzo() + " - Prezzo proposto : €" + proposta.getProposta()); 
		
		JButton bottoneInterazione = getBottoneAccetta(controller, proposta);
		JButton bottoneRifiuta = getBottoneRifiuta(controller, proposta);
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, utenteProposta, prezzo);
	}
	
	private JButton getBottoneAccetta(ControllerApp controller, PropostaVenditaDTO proposta) {
		JButton bottoneAccetta = new OutlinedButton("Accetta", verde);
		
		bottoneAccetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneAccettaClick(controller, proposta);
			}
		});
		
		return bottoneAccetta;
	}
	
	public void bottoneAccettaClick(ControllerApp controller, PropostaVenditaDTO proposta) {
		try {
			controller.accettaPropostaVendita(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
	}

	private JButton getBottoneRifiuta(ControllerApp controller, PropostaVenditaDTO proposta) {
		JButton bottoneRifiuta = new OutlinedButton("Rifiuta", rosso);
		
		bottoneRifiuta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRifiutaClick(controller, proposta);
			}
		});
		
		return bottoneRifiuta;
	}
	
	public void bottoneRifiutaClick(ControllerApp controller, PropostaVenditaDTO proposta) {
		try {
			controller.rifiutaPropostaVendita(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
	}

}
