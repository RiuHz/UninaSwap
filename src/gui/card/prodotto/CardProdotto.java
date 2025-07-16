package gui.card.prodotto;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.AppController;
import dto.ProdottoDTO;
import gui.card.CardDisplay;

public class CardProdotto extends CardDisplay {

	private static final long serialVersionUID = 1L;
	
	private ProdottoDTO prodotto;
	
	public CardProdotto(ProdottoDTO prodotto) {
		this.prodotto = prodotto;
		
		createComponents();
	}
	
	void createComponents() {
		JLabel image = getResizedImage(); // TODO Bisogna prenderlo dal DB, dio porco
		
		JLabel titolo = createLabel(prodotto.getNome());
		JTextArea descrizione = createTextArea("Descrizione : " + prodotto.getDescrizione());
		
		addToLayout(image, titolo, descrizione);
	}
	
	public int getIdProdotto() {
		return prodotto.getId();
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void addToLayout(JComponent image, JComponent ...components) {
    	GroupLayout layout = new GroupLayout(this);
    	setLayout(layout);

    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup leftColumn = layout.createParallelGroup();
    	GroupLayout.ParallelGroup centerColumn = layout.createParallelGroup();

    	GroupLayout.SequentialGroup centerColumnVertical = layout.createSequentialGroup();
    	
	    leftColumn.addComponent(image);
    	
    	for (JComponent component : components) {
    	    centerColumn.addComponent(component);
    	    centerColumnVertical.addComponent(component);
    	}


    	layout.setHorizontalGroup(
    	    layout.createSequentialGroup()
    	        .addGroup(leftColumn)
    	        .addGap(20)
    	        .addGroup(centerColumn)
    	);

    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    	        .addGroup(layout.createSequentialGroup().addComponent(image))
    	        .addGroup(centerColumnVertical)
    	);

        this.setLayout(layout);
    }

}
