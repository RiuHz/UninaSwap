package gui.dialog.richiesta;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controller.ControllerApp;
import dto.ProdottoDTO;
import dto.annunci.AnnuncioScambioDTO;
import exception.ProductNotSelectedException;
import gui.dialog.FinestraDialog;

public class DialogRichiediScambio extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	private AnnuncioScambioDTO annuncio;
	
	private JList<ProdottoDTO> listaProdotti;
	private JScrollPane pannelloProdotti;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);
	
	public DialogRichiediScambio(JFrame framePadre, ControllerApp controller, AnnuncioScambioDTO annuncio) {
		super(framePadre, "Proponi uno scambio per " + annuncio.getProdotto(), controller);
		
		this.controller = controller;
		this.annuncio = annuncio;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
    	listaProdotti = new JList<ProdottoDTO>(getProdottiElegibiliPerScambio().toArray(new ProdottoDTO[0]));
    	listaProdotti.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	
    	pannelloProdotti.setViewportView(listaProdotti);
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelProdotto = creaLabel("Seleziona i Prodotti da Scambiare");
    	listaProdotti = new JList<ProdottoDTO>(getProdottiElegibiliPerScambio().toArray(new ProdottoDTO[0]));
    	listaProdotti.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	pannelloProdotti = new JScrollPane(listaProdotti);
    	
    	aggiungiAlPannelloDialog(pannello, labelProdotto, pannelloProdotti);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Proponi Scambio", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	bottoneInterazioneClick();
            }
		});
		
		return bottone;
	}
	
	private void bottoneInterazioneClick() {
		try {	
			controller.creaPropostaScambio(annuncio, getProdottiSelezionati());
			
			nascondiDialog();
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	} catch (ProductNotSelectedException ProductError) {
			JOptionPane.showMessageDialog(this, "Non è stato selezionato nessun prodotto.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private ArrayList<ProdottoDTO> getProdottiSelezionati() throws ProductNotSelectedException {
		ArrayList<ProdottoDTO> listaProdottiSelezionati = new ArrayList<ProdottoDTO>();
		
		for (ProdottoDTO prodotto : listaProdotti.getSelectedValuesList())
			listaProdottiSelezionati.add(prodotto);
		
		if (listaProdottiSelezionati.isEmpty())
			throw new ProductNotSelectedException();
			
		return listaProdottiSelezionati;
	}
	
	private ArrayList<ProdottoDTO> getProdottiElegibiliPerScambio() {
		ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
		
		try {
			listaProdotti = controller.getProdottiElegibiliPerScambio();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	}
		
		return listaProdotti;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private JLabel creaLabel(String testo) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(nero);
        
        return label;
    }
    
    private void aggiungiAlPannelloDialog(JPanel pannello, JComponent ...componenti) {
		GroupLayout layout = new GroupLayout(pannello);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
		GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup();
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
        
		for (JComponent componente : componenti) {
			gruppoVerticale.addComponent(componente);
			gruppoOrizzontale.addComponent(componente);
		}
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
		
        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        pannello.setLayout(layout);
    }
	
}
