package gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import controller.ControllerApp;
import dto.proposte.PropostaDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;
import gui.card.proposta.CardProposta;
import gui.card.proposta.ricevuta.*;

public class PannelloProposteRicevute extends JPanel {

    private static final long serialVersionUID = 1L;

    private ControllerApp controller;
    
    private JPanel pannelloProposte = new JPanel();
    
	private final Font font = new Font("Verdana", Font.PLAIN, 20);
	private final Color nero = new Color(0x262626);

    PannelloProposteRicevute(FinestraApp finestra, ControllerApp controller) {
       this.controller = controller;
       
       creaComponenti(controller);
    }
	
	private ArrayList<PropostaDTO> getProposteRicevute() {
		ArrayList<PropostaDTO> listaProposte = new ArrayList<PropostaDTO>();
		
		try {
			listaProposte = controller.getProposteRicevute();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaProposte;
	}
	
	private CardProposta creaCardProposta(PropostaDTO proposta) {
        CardProposta card = switch (proposta) {
	        case PropostaRegaloDTO regalo -> new CardPropostaRegaloRicevuta(controller, regalo);
	        case PropostaVenditaDTO vendita -> new CardPropostaVenditaRicevuta(controller, vendita);
	        case PropostaScambioDTO scambio -> new CardPropostaScambioRicevuta(controller, scambio);
	        default -> throw new IllegalArgumentException("Non è stata creata una Card per questa Proposta Ricevuta");
        };

        return card;
	}
	
	public void aggiornaDati(ArrayList<PropostaDTO> listaProposteRicevute) {
		ArrayList<CardProposta> listaCard = new ArrayList<CardProposta>();
		
		pannelloProposte.removeAll();
		
		for (PropostaDTO annuncio: listaProposteRicevute)
			listaCard.add(creaCardProposta(annuncio));
		
		if (listaCard.size() > 0)
			aggiungiAlPannelloProposte(listaCard.toArray(new CardProposta[0]));

		pannelloProposte.repaint();
	}
	
    private void creaComponenti(ControllerApp controller) {
    	JPanel header = creaHeader();
    	header.setBackground(Color.white);
    	
    	JScrollPane pannelloScrollabile = new JScrollPane(pannelloProposte);
    	
    	pannelloProposte.setBackground(Color.white);
    	
    	setLayout(new BorderLayout());
      	setBackground(Color.white);
    	add(header, BorderLayout.NORTH);
    	add(pannelloScrollabile, BorderLayout.CENTER);
    	
    	aggiornaDati(getProposteRicevute());
    }
    
    private JPanel creaHeader() {
    	JPanel header = new JPanel();
    	
    	JButton bottoneHome = new UnderlineButton("Torna alla home", nero);
    	
    	bottoneHome.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			controller.mostra("Home");
    		}
    	});
    	
    	aggiungiAlPannelloHeader(header, bottoneHome);
    	
    	return header;
    }
    
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
    
    private void aggiungiAlPannelloHeader(JPanel header, JComponent componente) {
        GroupLayout layout = new GroupLayout(header);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup gruppoVertiale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        gruppoVertiale.addGap(0, 0, Integer.MAX_VALUE);
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        gruppoVertiale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        gruppoOrizzontale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        
        gruppoVertiale.addGap(0, 0, Integer.MAX_VALUE);
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        layout.setVerticalGroup(gruppoOrizzontale);
        layout.setHorizontalGroup(gruppoVertiale);
        
        header.setLayout(layout);
    }
    
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
        
        collegaGrandezzaComponenti(pannelloProposte, componenti);
    }

    private void collegaGrandezzaComponenti(JPanel pannello, JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) pannello.getLayout();

        layout.linkSize(SwingConstants.HORIZONTAL, componenti);
    }
    
    protected class UnderlineButton extends JButton {

    	private static final long serialVersionUID = 1L;

    	public UnderlineButton(String text, Color color) {
    		super(text);

    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}

    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(font.getAttributes());

    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);

    		Font underlineFont = font.deriveFont(underlineFontAttributes);

    		this.setFont(underlineFont);
    	}

        private void setButtonComponentSettings(JButton button, Color color) {
        	button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusable(false);
            button.setFont(font);
            button.setForeground(color);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

}
