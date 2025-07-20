package gui.card.prodotto;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import dto.ProdottoDTO;
import gui.card.Card;

public class CardProdotto extends Card {

	private static final long serialVersionUID = 1L;
	
	public CardProdotto(ProdottoDTO prodotto) {
		
		creaComponenti(prodotto);

	}
	
	private void creaComponenti(ProdottoDTO prodotto) {
		JLabel immagine = getImmagineRidimensionata(prodotto);
		
		JLabel titolo = creaLabel(prodotto.getNome());
		JLabel categoria = creaLabel("Categoria : " + prodotto.getCategoria());
		JTextArea descrizione = creaTextArea("Descrizione : " + prodotto.getDescrizione());
		
		aggiungiAlLayout(immagine, titolo, categoria, descrizione);
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private void aggiungiAlLayout(JComponent immagine, JComponent ...componenti) {
    	GroupLayout layout = new GroupLayout(this);

    	layout.setAutoCreateGaps(true);
    	layout.setAutoCreateContainerGaps(true);

    	GroupLayout.ParallelGroup colonnaSinistra = layout.createParallelGroup().addComponent(immagine);
    	GroupLayout.ParallelGroup colonnaCentrale = layout.createParallelGroup();

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
    	);

    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.CENTER)
    	        .addGroup(layout.createSequentialGroup().addComponent(immagine))
    	        .addGroup(colonnaCentraleVerticale)
    	);

        setLayout(layout);
    }

}
