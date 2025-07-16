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
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;
import gui.app.InventarioPanel.UnderlineButton;
import gui.card.annuncio.CardAnnuncio;
import gui.card.proposta.CardProposta;
import gui.card.proposta.inviata.CardPropostaRegaloInviata;
import gui.card.proposta.inviata.CardPropostaScambioInviata;
import gui.card.proposta.inviata.CardPropostaVenditaInviata;
import gui.card.proposta.ricevuta.CardPropostaRegaloRicevuta;
import gui.card.proposta.ricevuta.CardPropostaScambioRicevuta;
import gui.card.proposta.ricevuta.CardPropostaVenditaRicevuta;

class ProposteInviatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<CardProposta> listaCard = new ArrayList<CardProposta>();
	
	private AppController controller;
	
	private final Font verdanaFont = new Font("Verdana", Font.PLAIN, 20);
	private final Color black = new Color(0x262626);
	
	public ProposteInviatePanel(AppController controller) {
		this.controller = controller;
		
		createComponents();
	}

	protected void createComponents() {
		JScrollPane pannelloAnnunci = new JScrollPane(createPannelloProposte());
		
		setLayout(new BorderLayout());
		add(pannelloAnnunci, BorderLayout.CENTER);
	}
		
    private JPanel createPannelloProposte() {
    	JPanel pannello = new JPanel();
    	
    	for (PropostaRegaloDTO proposta: getProposteRegalo())
    		listaCard.add(new CardPropostaRegaloInviata(controller, proposta));
    	
    	for (PropostaVenditaDTO proposta: getProposteVendita())
    		listaCard.add(new CardPropostaVenditaInviata(controller, proposta));
    	
    	for (PropostaScambioDTO proposta: getProposteScambio())
    		listaCard.add(new CardPropostaScambioInviata(controller, proposta));
    	
    	listaCard.sort(Comparator.comparing(CardProposta::getIdProdotto));
    	
    	if (listaCard.size() != 0)
    		addToCenterLayout(pannello, listaCard.toArray(new CardProposta[0]));
    	
    	return pannello;
    }
    
	private ArrayList<PropostaRegaloDTO> getProposteRegalo() {
		ArrayList<PropostaRegaloDTO> listaAnnunci = new ArrayList<PropostaRegaloDTO>();
		
		try {
			listaAnnunci = controller.getProposteRegalo();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private ArrayList<PropostaVenditaDTO> getProposteVendita() {
		ArrayList<PropostaVenditaDTO> listaAnnunci = new ArrayList<PropostaVenditaDTO>();
		
		try {
			listaAnnunci = controller.getProposteVendita();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaAnnunci;
	}
	
	private ArrayList<PropostaScambioDTO> getProposteScambio() {
		ArrayList<PropostaScambioDTO> listaAnnunci = new ArrayList<PropostaScambioDTO>();
		
		try {
			listaAnnunci = controller.getProposteScambio();
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
