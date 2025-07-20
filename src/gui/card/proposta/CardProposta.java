package gui.card.proposta;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;

import dto.proposte.PropostaDTO;
import gui.card.Card;

public abstract class CardProposta extends Card {
	
	private static final long serialVersionUID = 1L;
	
	protected final Color verde = new Color(0x31E981);
	protected final Color arancione = new Color(0xFCB520);
	protected final Color rosso = new Color(0xDF2935);
		
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected JLabel creaLabel(String testo, Color colore) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(colore);
        
        return label;
    }
    
	protected JLabel getStato(PropostaDTO proposta) {
		JLabel stato = new JLabel();
		
		switch (proposta.getStato()) {
			case "Accettata" -> stato = creaLabel(proposta.getStato(), verde);
			case "Rifiutata" -> stato = creaLabel(proposta.getStato(), rosso);
			case "In Attesa" -> stato = creaLabel(proposta.getStato(), arancione);
		}
		
		return stato;
	}
	
    protected void aggiungiAlLayout(JComponent immagine, JComponent bottonePrincipale, JComponent bottoneSecondario, JComponent ...componenti) {
    	GroupLayout layout = new GroupLayout(this);

    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup colonnaSinistra = layout.createParallelGroup().addComponent(immagine);
    	GroupLayout.ParallelGroup colonnaCentrale = layout.createParallelGroup();
    	GroupLayout.ParallelGroup colonnaDestra = layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(bottonePrincipale).addComponent(bottoneSecondario);

    	GroupLayout.SequentialGroup colonnaCentraleVerticale = layout.createSequentialGroup();
    	GroupLayout.SequentialGroup colonnaDestraVerticale = layout.createSequentialGroup().addComponent(bottonePrincipale).addComponent(bottoneSecondario);
    	
    	for (JComponent componente : componenti) {
    	    colonnaCentrale.addComponent(componente);
    	    colonnaCentraleVerticale.addComponent(componente);
    	}

    	layout.setHorizontalGroup(
    	    layout.createSequentialGroup()
    	        .addGroup(colonnaSinistra)
    	        .addGap(20)
    	        .addGroup(colonnaCentrale)
    	        .addGap(20)
    	        .addGroup(colonnaDestra)
    	);

    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    	        .addGroup(layout.createSequentialGroup().addComponent(immagine))
    	        .addGroup(colonnaCentraleVerticale)
    	        .addGroup(colonnaDestraVerticale)
    	);

        setLayout(layout);
    }
    	
}
