package gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.AppController;
import dto.ProdottoDTO;
import gui.card.annuncio.CardAnnuncio;
import gui.card.prodotto.CardProdotto;

class InventarioPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<CardProdotto> listaCard = new ArrayList<CardProdotto>();
	
	private AppController controller;
	
	private final Font verdanaFont = new Font("Verdana", Font.PLAIN, 20);
	private final Color black = new Color(0x262626);
	
	public InventarioPanel(AppController controller) {
		this.controller = controller;
		
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
		
		JButton aggiungiProdotto = new UnderlineButton("Aggiungi un Prodotto", black);
		
		JButton aggiungiAnnuncio = new UnderlineButton("Aggiungi un Annuncio", black);
		
//        buttonFiltri.addMouseListener(new MouseAdapter() {
//        	@Override
//            public void mouseClicked(MouseEvent e) {
//        		finestraFiltri.showDialog();
//            }
//        });
		
		addToPageHeaderLayout(pageHeader, aggiungiProdotto, aggiungiAnnuncio);
		
		return pageHeader;
	}
	
	protected JPanel createPannelloAnnunci() {
		JPanel pannello = new JPanel();
		
		for (ProdottoDTO prodotto: getProdottiUtente())
			listaCard.add(new CardProdotto(prodotto));
		
		listaCard.sort(Comparator.comparing(CardProdotto::getIdProdotto));
		
		if (listaCard.size() != 0)
			addToCenterLayout(pannello, listaCard.toArray(new CardAnnuncio[0]));
		
		return pannello;
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
    
    protected void addToCenterLayout(JPanel panel, JComponent ...components) {
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
        
        linkCenterLayoutSize(panel, components);
    }

    protected void linkCenterLayoutSize(JPanel panel, JComponent ...components) {
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
