package gui.card.proposta.ricevuta;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.ProdottoDTO;
import dto.proposte.PropostaScambioDTO;
import gui.card.proposta.CardProposta;

public class CardPropostaScambioRicevuta extends CardProposta {

	private static final long serialVersionUID = 1L;
	
	public CardPropostaScambioRicevuta(ControllerApp controller, PropostaScambioDTO proposta) {
		
		createComponents(controller, proposta);
		
	}
	
	private void createComponents(ControllerApp controller, PropostaScambioDTO proposta) {
		JLabel immagine = getImmagineRidimensionata(proposta.getAnnuncio().getProdotto());
		
		JLabel titolo = creaLabel(proposta.getAnnuncio().getProdotto().getNome());
		JLabel categoria = creaLabel("Categoria : " + proposta.getAnnuncio().getProdotto().getCategoria());
		JLabel utenteProposta = creaLabel("Proposta ricevuta da : " + proposta.getUtente());
		JTextArea oggettiProposti = creaTextArea(getOggettiProposti(proposta)); 
		
		JButton bottoneInterazione = getBottoneAccetta(controller, proposta);
		JButton bottoneRifiuta = getBottoneRifiuta(controller, proposta);
		
		aggiungiAlLayout(immagine, bottoneInterazione, bottoneRifiuta, titolo, categoria, utenteProposta, oggettiProposti);
	}
	
	private JButton getBottoneAccetta(ControllerApp controller, PropostaScambioDTO proposta) {
		JButton bottoneAccetta = new OutlinedButton("Accetta", verde);
		
		bottoneAccetta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneAccettaClick(controller, proposta);
			}
		});
		
		return bottoneAccetta;
	}
	
	public void bottoneAccettaClick(ControllerApp controller, PropostaScambioDTO proposta) {
		try {
			controller.accettaPropostaScambio(proposta);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
	}

	private JButton getBottoneRifiuta(ControllerApp controller, PropostaScambioDTO proposta) {
		JButton bottoneRifiuta = new OutlinedButton("Rifiuta", rosso);
		
		bottoneRifiuta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bottoneRifiutaClick(controller, proposta);
			}
		});
		
		return bottoneRifiuta;
	}
	
	public void bottoneRifiutaClick(ControllerApp controller, PropostaScambioDTO proposta) {
		try {
			controller.rifiutaPropostaScambio(proposta);
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

}
