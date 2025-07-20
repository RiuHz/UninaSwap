package gui.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import controller.ControllerApp;
import dto.ProdottoDTO;
import dto.proposte.PropostaDTO;

class PannelloProfilo extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private PannelloInventario pannelloInventario;
	private PannelloProposteInviate pannelloProposteInviate;
	private PannelloStorico pannelloStorico;
	
	protected final Font font = new Font("Verdana", Font.PLAIN, 18);	
	protected final Color nero = new Color(0x262626);
	
	public PannelloProfilo(JFrame finestra, ControllerApp controller) {
		this.controller = controller;
		
		creaTab(finestra);
	}
	
	public void aggiornaInventario(ArrayList<ProdottoDTO> listaProdotti) {
		pannelloInventario.aggiornaProdotti(listaProdotti);
	}
	
	public void aggiornaProposteInviate(ArrayList<PropostaDTO> listaProposte) {
		pannelloProposteInviate.aggiornaProposte(listaProposte);
	}
	

	public void aggiornaStorico() {
		pannelloStorico.aggiornaDati();	
	}
	
	private void creaTab(JFrame finestra) {
		pannelloInventario = new PannelloInventario(finestra, controller);
		pannelloProposteInviate = new PannelloProposteInviate(finestra, controller);
		pannelloStorico = new PannelloStorico(controller);
		
		setBackground(Color.white);
		setUI(new CenteredTabbedPaneUI());
		
		add(pannelloInventario);
		add(pannelloProposteInviate);
		add(pannelloStorico);
		
		setTabComponentAt(0, creaLabel("I miei oggetti"));
		setTabComponentAt(1, creaLabel("Le mie proposte"));
		setTabComponentAt(2, creaLabel("Storico"));
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private JLabel creaLabel(String testo) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(nero);
        
        return label;
    }

    private class CenteredTabbedPaneUI extends BasicTabbedPaneUI {
        @Override
        protected LayoutManager createLayoutManager() {
            return new TabbedPaneLayout() {
                @Override
                protected void calculateTabRects(int tabPlacement, int tabCount) {
                    super.calculateTabRects(tabPlacement, tabCount);

                    if (tabPlacement == TOP || tabPlacement == BOTTOM) {
                        int totalWidth = 0;
                        for (int i = 0; i < tabCount; i++) {
                            totalWidth += rects[i].width;
                        }

                        int offset = (tabPane.getWidth() - totalWidth) / 2;
                        for (int i = 0; i < tabCount; i++) {
                            rects[i].x += offset;
                        }
                    }
                }
            };
        }
    }
    
}
