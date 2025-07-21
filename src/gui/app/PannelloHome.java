package gui.app;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import controller.ControllerApp;
import dto.annunci.AnnuncioDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import gui.card.annuncio.CardAnnuncio;
import gui.card.annuncio.CardAnnuncioRegalo;
import gui.card.annuncio.CardAnnuncioScambio;
import gui.card.annuncio.CardAnnuncioVendita;
import gui.dialog.FinestraDialog;
import gui.dialog.filtro.DialogFiltri;

class PannelloHome extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JFrame finestra;
	private JPanel pannelloAnnunci = new JPanel();
	private FinestraDialog dialogFiltri;

	private final Font font = new Font("Verdana", Font.PLAIN, 20);
	private final Color nero = new Color(0x262626);
	
	PannelloHome (JFrame finestra, ControllerApp controller) {
		this.controller = controller;
		this.finestra = finestra;
		this.dialogFiltri = new DialogFiltri(finestra, controller);
		
		creaComponenti();
	}
	
	private ArrayList<AnnuncioDTO> getAnnunciRecenti() {
		ArrayList<AnnuncioDTO> listaAnnunci = new ArrayList<AnnuncioDTO>();
		
		try {
			listaAnnunci = controller.getAnnunciRecenti();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private CardAnnuncio creaCardAnnuncio(AnnuncioDTO annuncio) {
        CardAnnuncio card = switch (annuncio) {
	        case AnnuncioRegaloDTO regalo -> new CardAnnuncioRegalo(finestra, controller, regalo);
	        case AnnuncioVenditaDTO vendita -> new CardAnnuncioVendita(finestra, controller, vendita);
	        case AnnuncioScambioDTO scambio -> new CardAnnuncioScambio(finestra, controller, scambio);
	        default -> throw new IllegalArgumentException("Non è stata creata una Card per questo Annuncio");
        };

        return card;
	}
	
	public void aggiornaDati(ArrayList<AnnuncioDTO> listaAnnunci) {
		ArrayList<CardAnnuncio> listaCard = new ArrayList<CardAnnuncio>();
		
		pannelloAnnunci.removeAll();
		
		for (AnnuncioDTO annuncio: listaAnnunci)
			listaCard.add(creaCardAnnuncio(annuncio));
		
		if (listaCard.size() > 0)
			aggiungiAlPannelloAnnunci(listaCard.toArray(new CardAnnuncio[0]));
		
		pannelloAnnunci.repaint();
	}
	
	protected void creaComponenti() {
		JPanel pannelloHeader = creaHeader();
		pannelloHeader.setBackground(Color.white);
		pannelloAnnunci.setBackground(Color.white);
		
		JScrollPane pannelloScrollabile = new JScrollPane(pannelloAnnunci);
		
		setLayout(new BorderLayout());
		add(pannelloHeader, BorderLayout.NORTH);
		add(pannelloScrollabile, BorderLayout.CENTER);
		
		aggiornaDati(getAnnunciRecenti());
	}
	
	protected JPanel creaHeader() {
		JPanel header = new JPanel();
		
		JLabel labelHeader = creaLabel("Gli ultimi annunci su UninaSwap", nero);
		
		JButton bottoneHeader = new UnderlineButton("Filtri", nero);
		
        bottoneHeader.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		dialogFiltri.mostraDialog();
            }
        });
		
		aggiungiAlPannelloHeader(header, labelHeader, bottoneHeader);
		
		return header;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void aggiungiAlPannelloHeader(JPanel header, JComponent ...componenti) {
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
    
    protected void aggiungiAlPannelloAnnunci(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(pannelloAnnunci);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        gruppoVerticale.addGap(0, 0, 50);
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent component : componenti) {
            gruppoVerticale.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            gruppoOrizzontale.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        gruppoVerticale.addGap(0, 0, 50);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);

        pannelloAnnunci.setLayout(layout);
        
        collegaGrandezzaComponenti(pannelloAnnunci, componenti);
    }

    private void collegaGrandezzaComponenti(JPanel pannello, JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) pannello.getLayout();

        layout.linkSize(SwingConstants.HORIZONTAL, componenti);
    }
	
    private JLabel creaLabel(String testo, Color colore) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(colore);
        
        return label;
    }
    
    private class UnderlineButton extends JButton {

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
