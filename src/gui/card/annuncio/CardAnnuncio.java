package gui.card.annuncio;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.annunci.AnnuncioDTO;
import gui.card.Card;

public abstract class CardAnnuncio extends Card {

	private static final long serialVersionUID = 1L;

	protected CardAnnuncio(ControllerApp controller, AnnuncioDTO annuncio) {
		
		creaComponenti(controller, annuncio);
		
	}
	
	protected abstract JButton getBottoneAnnuncio(ControllerApp controller);
	
	protected void creaComponenti(ControllerApp controller, AnnuncioDTO annuncio) {
		JLabel immagine = getImmagineRidimensionata(annuncio.getProdotto());
		
		JLabel titolo = creaLabel(annuncio.getProdotto().getNome() + " di " + annuncio.getProdotto().getUtente());
		JLabel categoria = creaLabel("Categoria : " + annuncio.getProdotto().getCategoria());
		JTextArea descrizione = creaTextArea("Descrizione : " + annuncio.getProdotto().getDescrizione());
		JTextArea consegna = creaTextArea("Consegna : " + annuncio.getConsegna());
		JLabel data = creaLabel("Pubblicato : " + annuncio.getData());
		
		JButton bottone = getBottoneAnnuncio(controller);
		
		aggiungiAlLayout(immagine, bottone, titolo, categoria, descrizione, consegna, data);
	}
		
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected void aggiungiAlLayout(JComponent immagine, JComponent bottone, JComponent ...componenti) {
    	GroupLayout layout = new GroupLayout(this);

    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup colonnaSinistra = layout.createParallelGroup().addComponent(immagine);
    	GroupLayout.ParallelGroup colonnaCentrale = layout.createParallelGroup();
    	GroupLayout.ParallelGroup colonnaDestra = layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(bottone);

    	GroupLayout.SequentialGroup colonnaCentraleVerticale = layout.createSequentialGroup();
    	
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
    	        .addGroup(layout.createSequentialGroup().addComponent(bottone))
    	);

        setLayout(layout);
    }

}
