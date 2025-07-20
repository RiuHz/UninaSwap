package gui.dialog.proposta;

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
import dto.proposte.PropostaScambioDTO;
import exception.ProductNotSelectedException;
import gui.dialog.FinestraDialog;

public class DialogModificaPropostaScambio extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	private PropostaScambioDTO proposta;
	
	private JList<ProdottoDTO> listaProdotti;
	private JScrollPane pannelloProdotti;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	public DialogModificaPropostaScambio(JFrame framePadre, ControllerApp controller, PropostaScambioDTO proposta) {
		super(framePadre, "Modifica scambio per " + proposta.getAnnuncio().getProdotto(), controller);
		
		this.controller = controller;
		this.proposta = proposta;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {		
    	aggiungiProdottiAllaLista();
    	selezionaProdottiInProposta();
    	
    	pannelloProdotti.setViewportView(listaProdotti);
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelProdotto = creaLabel("Seleziona i Prodotti da Scambiare");
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
			controller.modificaPropostaScambio(proposta, getProdottiSelezionati());
			
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
	
	private void aggiungiProdottiAllaLista() {
		ArrayList<ProdottoDTO> prodottiElegibili = new ArrayList<ProdottoDTO>(proposta.getListaProdotti());
		prodottiElegibili.addAll(getProdottiElegibiliPerScambio());
		
		listaProdotti = new JList<ProdottoDTO>(prodottiElegibili.toArray(new ProdottoDTO[0]));
	}
	
	private void selezionaProdottiInProposta() {
    	listaProdotti.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
    	int indiciDaSelezionare[] = new int[proposta.getListaProdotti().size()];
    	
    	System.out.println(proposta.getListaProdotti().size());
		
    	for (int i = 0; i < proposta.getListaProdotti().size(); i++)
    		indiciDaSelezionare[i] = i;
    	
    	listaProdotti.clearSelection();
    	listaProdotti.setSelectedIndices(indiciDaSelezionare);
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
