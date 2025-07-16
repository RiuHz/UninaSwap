package gui.card.proposta;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

import dto.proposte.PropostaDTO;
import gui.card.CardDisplay;

public abstract class CardProposta extends CardDisplay {
	
	private static final long serialVersionUID = 1L;
	
	protected final Color green = new Color(0x31E981);
	protected final Color orange = new Color(0xFCB520);
	protected final Color red = new Color(0xDF2935);
	
	public abstract int getIdProdotto();
		
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void addToLayout(JComponent image, JComponent buttonInteraction, JComponent buttonReject, JComponent ...components) {
    	GroupLayout layout = new GroupLayout(this);
    	setLayout(layout);

    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup leftColumn = layout.createParallelGroup();
    	GroupLayout.ParallelGroup centerColumn = layout.createParallelGroup();
    	GroupLayout.ParallelGroup rightColumn = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

    	GroupLayout.SequentialGroup centerColumnVertical = layout.createSequentialGroup();
    	
	    leftColumn.addComponent(image);
    	
    	for (JComponent component : components) {
    	    centerColumn.addComponent(component);
    	    centerColumnVertical.addComponent(component);
    	}

	    rightColumn.addComponent(buttonInteraction);
	    rightColumn.addComponent(buttonReject);

    	layout.setHorizontalGroup(
    	    layout.createSequentialGroup()
    	        .addGroup(leftColumn)
    	        .addGap(20)
    	        .addGroup(centerColumn)
    	        .addGap(20)
    	        .addGroup(rightColumn)
    	);

    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    	        .addGroup(layout.createSequentialGroup().addComponent(image))
    	        .addGroup(centerColumnVertical)
    	        .addGroup(layout.createSequentialGroup().addComponent(buttonInteraction).addComponent(buttonReject))
    	);

        this.setLayout(layout);
    }
    
    protected JLabel createLabel(String text, Color color) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont);
    	label.setForeground(color);
    	label.setMaximumSize(new Dimension(150, 100));
        
        return label;
    }
    
	protected JLabel getStato(PropostaDTO proposta) {
		JLabel stato = new JLabel();
		
		switch (proposta.getStato()) {
			case "Accettata" -> stato = createLabel(proposta.getStato(), green);
			case "Rifiutata" -> stato = createLabel(proposta.getStato(), red);
			case "In Attesa" -> stato = createLabel(proposta.getStato(), orange);
		}
		
		return stato;
	}
	
}
