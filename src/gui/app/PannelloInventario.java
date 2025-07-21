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

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.ControllerApp;
import dto.ProdottoDTO;
import gui.card.prodotto.CardProdotto;
import gui.dialog.FinestraDialog;
import gui.dialog.annuncio.DialogAggiungiAnnuncio;
import gui.dialog.prodotto.DialogAggiungiProdotto;

class PannelloInventario extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JPanel pannelloProdotti = new JPanel();
	private FinestraDialog dialogAggiungiProdotto;
	private FinestraDialog dialogAggiungiAnnuncio;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 20);
	private final Color nero = new Color(0x262626);
	
	public PannelloInventario(JFrame finestra, ControllerApp controller) {
		this.controller = controller;
		this.dialogAggiungiProdotto = new DialogAggiungiProdotto(finestra, controller);
		this.dialogAggiungiAnnuncio = new DialogAggiungiAnnuncio(finestra, controller);
		
		creaComponenti();
	}
	
	private ArrayList<ProdottoDTO> getProdottiUtente() {
		ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
		
		try {
			listaProdotti = controller.getProdottiUtente();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaProdotti;
	}
	

	public void aggiornaProdotti(ArrayList<ProdottoDTO> listaProdotti) {
		ArrayList<CardProdotto> listaCard = new ArrayList<CardProdotto>();
		
		pannelloProdotti.removeAll();
		
		for (ProdottoDTO prodotto: listaProdotti)
			listaCard.add(new CardProdotto(prodotto));
		
		if (listaCard.size() > 0)
			aggiungiAlPannelloAnnunci(listaCard.toArray(new CardProdotto[0]));
	
		pannelloProdotti.repaint();
	}

	protected void creaComponenti() {
		JPanel header = creaHeader();
		
		JScrollPane pannelloScrollabile = new JScrollPane(pannelloProdotti);
		
		pannelloProdotti.setBackground(Color.white);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(pannelloScrollabile, BorderLayout.CENTER);
		
		aggiornaProdotti(getProdottiUtente());
	}
	
	protected JPanel creaHeader() {
		JPanel header = new JPanel();
		header.setBackground(Color.white);
		
		JButton bottoneAggiungiProdotto = new UnderlineButton("Aggiungi un Prodotto", nero);
		
		JButton bottoneAggiungiAnnuncio = new UnderlineButton("Aggiungi un Annuncio", nero);
		
        bottoneAggiungiProdotto.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		dialogAggiungiProdotto.mostraDialog();
            }
        });
        
        bottoneAggiungiAnnuncio.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		dialogAggiungiAnnuncio.mostraDialog();
            }
        });
		
		aggiungiAlPannelloHeader(header, bottoneAggiungiProdotto, bottoneAggiungiAnnuncio);
		
		return header;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private void aggiungiAlPannelloHeader(JPanel header, JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(header);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        for (int index = 0; index < componenti.length; index++) {
            gruppoVerticale.addComponent(componenti[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            
            if (index < componenti.length - 1)
                gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
            
            gruppoOrizzontale.addComponent(componenti[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        
        layout.setVerticalGroup(gruppoOrizzontale);
        layout.setHorizontalGroup(gruppoVerticale);
        
        header.setLayout(layout);
    }
    
    private void aggiungiAlPannelloAnnunci(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(pannelloProdotti);

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

        pannelloProdotti.setLayout(layout);
        
        collegaGrandezzaElementi(pannelloProdotti, componenti);
    }

    private void collegaGrandezzaElementi(JPanel pannello, JComponent ...componenti) {
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
