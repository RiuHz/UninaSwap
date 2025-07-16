package gui.card.annuncio;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import dto.annunci.AnnuncioDTO;
import gui.card.CardDisplay;

public abstract class CardAnnuncio extends CardDisplay {

	private static final long serialVersionUID = 1L;
	private AnnuncioDTO annuncio;

	protected CardAnnuncio(AnnuncioDTO annuncio) {
		this.annuncio = annuncio;
		
		createComponents();
	}
	
	void createComponents() {
		JLabel image = getResizedImage(); // TODO Bisogna prenderlo dal DB, dio porco
		
		JLabel titolo = createLabel(annuncio.prodotto.getNome() + " di " + annuncio.prodotto.getUser());
		JTextArea descrizione = createTextArea("Descrizione : " + annuncio.prodotto.getDescrizione());
		JTextArea consegna = createTextArea("Consegna : " + annuncio.getConsegna());
		JLabel data = createLabel("Pubblicato : " + annuncio.getData());
		
		JButton bottone = getButtonAnnuncio();
		
		addToLayout(image, bottone, titolo, descrizione, consegna, data);
	}
	
	protected abstract JButton getButtonAnnuncio();
	
	public String getDataAnnuncio() {
		return annuncio.getData();
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void addToLayout(JComponent image, JComponent button, JComponent ...components) {
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

	    rightColumn.addComponent(button);

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
    	        .addGroup(layout.createSequentialGroup().addComponent(button))
    	);

        this.setLayout(layout);
    }

}
