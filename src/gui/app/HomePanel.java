package gui.app;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import javax.swing.*;

import controller.AppController;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;
import gui.card.annuncio.CardAnnuncio;
import gui.card.annuncio.CardAnnuncioRegalo;
import gui.card.annuncio.CardAnnuncioScambio;
import gui.card.annuncio.CardAnnuncioVendita;
import gui.dialog.FiltroDialog;

class HomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AppController controller;
	
	private ArrayList<CardAnnuncio> listaCard = new ArrayList<CardAnnuncio>();
	
	private FiltroDialog finestraFiltri;

	private final Font verdanaFont = new Font("Verdana", Font.PLAIN, 20);
	private final Color black = new Color(0x262626);
	
	HomePanel (AppController controller, FiltroDialog finestraFiltri) {
		this.controller = controller;
		this.finestraFiltri = finestraFiltri;
		
		createComponents();
	}
	
	protected void createComponents() {
		JPanel pageHeader = createPageHeader();
		
		JScrollPane pannelloAnnunci = new JScrollPane(createPannelloAnnunci());
		
		setLayout(new BorderLayout());
		add(pageHeader, BorderLayout.NORTH);
		add(pannelloAnnunci, BorderLayout.CENTER);
	}
	
	protected JPanel createPageHeader() {
		JPanel pageHeader = new JPanel();
		
		JLabel latestListingsLabel = createLabel("Gli ultimi annunci su UninaSwap", black);
		
		JButton buttonFiltri = new UnderlineButton("Filtri", black);
		
        buttonFiltri.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		finestraFiltri.showDialog();
            }
        });
		
		addToPageHeaderLayout(pageHeader, latestListingsLabel, buttonFiltri);
		
		return pageHeader;
	}
	
	protected JPanel createPannelloAnnunci() {
		JPanel pannello = new JPanel();
		
		for (AnnuncioRegaloDTO annuncio: getAnnunciRegaloRecenti())
			listaCard.add(new CardAnnuncioRegalo(controller, annuncio));
		
		for (AnnuncioVenditaDTO annuncio: getAnnunciVenditaRecenti())
			listaCard.add(new CardAnnuncioVendita(controller, annuncio));
		
		for (AnnuncioScambioDTO annuncio: getAnnunciScambioRecenti())
			listaCard.add(new CardAnnuncioScambio(controller, annuncio));
		
		listaCard.sort(Comparator.comparing(CardAnnuncio::getDataAnnuncio).reversed());
		
		if (listaCard.size() != 0)
			addToHomeLayout(pannello, listaCard.toArray(new CardAnnuncio[0]));
		
		return pannello;
	}
	
	private ArrayList<AnnuncioRegaloDTO> getAnnunciRegaloRecenti() {
		ArrayList<AnnuncioRegaloDTO> listaAnnunci = new ArrayList<AnnuncioRegaloDTO>();
		
		try {
			listaAnnunci = controller.getAnnunciRegaloRecenti();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private ArrayList<AnnuncioVenditaDTO> getAnnunciVenditaRecenti() {
		ArrayList<AnnuncioVenditaDTO> listaAnnunci = new ArrayList<AnnuncioVenditaDTO>();
		
		try {
			listaAnnunci = controller.getAnnunciVenditaRecenti();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private ArrayList<AnnuncioScambioDTO> getAnnunciScambioRecenti() {
		ArrayList<AnnuncioScambioDTO> listaAnnunci = new ArrayList<AnnuncioScambioDTO>();
		
		try {
			listaAnnunci = controller.getAnnunciScambioRecenti();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void addToPageHeaderLayout(JPanel header, JComponent ...components) {
        GroupLayout layout = new GroupLayout(header);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        for (int index = 0; index < components.length; index++) {
            verticalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            
            if (index < components.length - 1)
                verticalGroup.addGap(0, 0, Integer.MAX_VALUE);
            
            horizontalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        
        layout.setVerticalGroup(horizontalGroup);
        layout.setHorizontalGroup(verticalGroup);
        
        header.setLayout(layout);
    }
    
    protected void addToHomeLayout(JPanel panel, JComponent ...components) {
        GroupLayout layout = new GroupLayout(panel);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        verticalGroup.addGap(0, 0, 50);
        horizontalGroup.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent component : components) {
            verticalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            horizontalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        verticalGroup.addGap(0, 0, 50);

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);

        panel.setLayout(layout);
        
        linkHomeLayoutSize(panel, components);
    }

    protected void linkHomeLayoutSize(JPanel panel, JComponent ...components) {
    	GroupLayout layout = (GroupLayout) panel.getLayout();

        layout.linkSize(SwingConstants.HORIZONTAL, components);
    }
	
    protected JLabel createLabel(String text, Color color) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont);
    	label.setForeground(color);
        
        return label;
    }
    
    protected class UnderlineButton extends JButton {

    	private static final long serialVersionUID = 1L;

    	public UnderlineButton(String text, Color color) {
    		super(text);

    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}

    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(verdanaFont.getAttributes());

    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);

    		Font underlineFont = verdanaFont.deriveFont(underlineFontAttributes);

    		this.setFont(underlineFont);
    	}

        private void setButtonComponentSettings(JButton button, Color color) {
        	button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusable(false);
            button.setFont(verdanaFont);
            button.setForeground(color);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
	
}
