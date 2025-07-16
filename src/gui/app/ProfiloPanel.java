package gui.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

import controller.AppController;

class ProfiloPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private AppController controller;
	
	protected final Font verdanaFont = new Font("Verdana", Font.PLAIN, 18);	
	protected final Color black = new Color(0x262626);
	
	ProfiloPanel (AppController controller) {
		this.controller = controller;
		
		createTabs();
	}
	
	private void createTabs() {
		JPanel panelInventario = new InventarioPanel(controller);
		JPanel panelOfferteInviate = new ProposteInviatePanel(controller);
		JPanel panelStorico = new StoricoPanel(controller);
		
		add(panelInventario);
		add(panelOfferteInviate);
		add(panelStorico);
		
		setTabComponentAt(0, createLabel("I miei oggetti"));
		setTabComponentAt(1, createLabel("Le mie proposte"));
		setTabComponentAt(2, createLabel("Storico"));
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected JLabel createLabel(String text) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont);
    	label.setForeground(black);
    	label.setMaximumSize(new Dimension(150, 100));
        
        return label;
    }

}
