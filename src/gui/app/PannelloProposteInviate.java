package gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.ControllerApp;
import dto.proposte.PropostaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;
import gui.card.proposta.inviata.CardPropostaRegaloInviata;
import gui.card.proposta.inviata.CardPropostaScambioInviata;
import gui.card.proposta.inviata.CardPropostaVenditaInviata;

class PannelloProposteInviate extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JFrame finestra;
	private JPanel pannelloProposte = new JPanel();
	
	public PannelloProposteInviate(JFrame finestra, ControllerApp controller) {
		this.controller = controller;
		this.finestra = finestra;
		
		creaComponenti();
	}
	
	
	private ArrayList<PropostaDTO> getProposteInviate() {
		ArrayList<PropostaDTO> listaAnnunci = new ArrayList<PropostaDTO>();
		
		try {
			listaAnnunci = controller.getProposteInviate();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private CardProposta creaCardProposta(PropostaDTO proposta) {
        CardProposta card = switch (proposta) {
	        case PropostaRegaloDTO regalo -> new CardPropostaRegaloInviata(finestra, controller, regalo);
	        case PropostaVenditaDTO vendita -> new CardPropostaVenditaInviata(finestra, controller, vendita);
	        case PropostaScambioDTO scambio -> new CardPropostaScambioInviata(finestra, controller, scambio);
	        default -> throw new IllegalArgumentException("Non è stata creata una Card per questa Proposta Inviata");
        };

        return card;
	}
	
	public void aggiornaProposte(ArrayList<PropostaDTO> listaProposte) {
		ArrayList<CardProposta> listaCard = new ArrayList<CardProposta>();
		
		pannelloProposte.removeAll();
		
		for (PropostaDTO proposta: listaProposte)
			listaCard.add(creaCardProposta(proposta));
		
		if (listaCard.size() > 0)
			aggiungiAlPannelloProposte(listaCard.toArray(new CardProposta[0]));
		
		pannelloProposte.repaint();
	}

	protected void creaComponenti() {
		JScrollPane pannelloScrollabile = new JScrollPane(pannelloProposte);
		pannelloProposte.setBackground(Color.white);
		
		setLayout(new BorderLayout());
		add(pannelloScrollabile, BorderLayout.CENTER);
		
		aggiornaProposte(getProposteInviate());
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
    
    private void aggiungiAlPannelloProposte(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(pannelloProposte);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        gruppoVerticale.addGap(0, 0, 50);
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent componente : componenti) {
            gruppoVerticale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            gruppoOrizzontale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        gruppoVerticale.addGap(0, 0, 50);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);

        pannelloProposte.setLayout(layout);
        
        collegaGrandezzaElementi(componenti);
    }

    private void collegaGrandezzaElementi(JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) pannelloProposte.getLayout();

        layout.linkSize(SwingConstants.HORIZONTAL, componenti);
    }
	
}
