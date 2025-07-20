package gui.dialog.annuncio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.ControllerApp;
import dto.ProdottoDTO;
import exception.ProductNotSelectedException;
import gui.dialog.FinestraDialog;

public class DialogAggiungiAnnuncio extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JList<ProdottoDTO> listaProdotti;
	private JTextArea textAreaConsegna = creaTextArea();
	private JScrollPane pannelloProdotti;
	private ArrayList<JRadioButton> tipologie = new ArrayList<JRadioButton>();
	private JLabel labelPrezzo;
	private JTextField textFieldPrezzo;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	public DialogAggiungiAnnuncio(JFrame finestra, ControllerApp controller) {
		super(finestra, "Aggiungi Annuncio", controller);
		
    	this.controller = controller;

    	creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
    	listaProdotti = new JList<ProdottoDTO>(getProdottiElegibiliPerAnnuncio().toArray(new ProdottoDTO[0]));
    	pannelloProdotti.setViewportView(listaProdotti);
    	textAreaConsegna.setText("");
    	textFieldPrezzo.setText("");
    	
    	labelPrezzo.setVisible(false);
    	textFieldPrezzo.setVisible(false);
    	
    	for (JRadioButton bottone : tipologie)
    		bottone.setSelected(false);
    	
    	System.out.println("Aggiornato"); // TODO Test
	}
	
	private String getTipologiaSelezionata() {
		String tipologia = new String();
		
		for (JRadioButton bottone : tipologie)
			if (bottone.isSelected())
				tipologia = bottone.getText();
		
		return tipologia;
	}
	
	private ProdottoDTO getProdottoSelezionato() throws ProductNotSelectedException {
		ProdottoDTO prodotto = listaProdotti.getSelectedValue();
		
		if (prodotto == null)
			throw new ProductNotSelectedException();
			
		return prodotto;
	}
	
	private ArrayList<ProdottoDTO> getProdottiElegibiliPerAnnuncio() {
		ArrayList<ProdottoDTO> listaProdotti = new ArrayList<ProdottoDTO>();
		
		try {
			listaProdotti = controller.getProdottiElegibiliPerAnnuncio();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	}
		
		return listaProdotti;
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelProdotto = creaLabel("Seleziona un Prodotto");
    	pannelloProdotti = new JScrollPane(listaProdotti);
    	
    	JLabel labelConsegna = creaLabel("Inserisci la modalità di Consegna");
    	JScrollPane pannelloConsegna = new JScrollPane(textAreaConsegna);
    	
    	JLabel labelTipologia = creaLabel("Tipologia");
    	JRadioButton regalo = creaRadioButton("Regalo");
    	JRadioButton vendita = creaRadioButton("Vendita");
    	JRadioButton scambio = creaRadioButton("Scambio");
    	
    	ButtonGroup gruppoTipologia = new ButtonGroup();
    	gruppoTipologia.add(regalo);
    	gruppoTipologia.add(vendita);
    	gruppoTipologia.add(scambio);
    	
    	tipologie.add(regalo);
    	tipologie.add(vendita);
    	tipologie.add(scambio);
    	
    	labelPrezzo = creaLabel("Inserisci un Prezzo");
    	textFieldPrezzo = creaTextField();
    	
    	vendita.addItemListener(new ItemListener() {
    		@Override
    		public void itemStateChanged(ItemEvent e) {
    			labelPrezzo.setVisible(vendita.isSelected());
    			textFieldPrezzo.setVisible(vendita.isSelected());
    		}
    	});
    	
    	aggiungiAlPannelloDialog(pannello, labelProdotto, labelConsegna, pannelloConsegna, labelTipologia, regalo, vendita, scambio, labelPrezzo);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Aggiungi Annuncio", nero);
		
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
			ProdottoDTO prodottoSelezionato = getProdottoSelezionato();
			String consegna = textAreaConsegna.getText();
			String tipologia = getTipologiaSelezionata();
			
			switch (tipologia) {
				case "Regalo" -> controller.creaAnnuncioRegalo(prodottoSelezionato, consegna);
				case "Scambio" -> controller.creaAnnuncioScambio(prodottoSelezionato, consegna);
				case "Vendita" -> controller.creaAnnuncioVendita(prodottoSelezionato, consegna, Double.valueOf(textFieldPrezzo.getText()));
			}
			
			nascondiDialog();
		} catch (NumberFormatException NumberFormatoError) {
			JOptionPane.showMessageDialog(this, "Non è stato inserito un prezzo valido.", "Errore", JOptionPane.ERROR_MESSAGE);
		} catch (ProductNotSelectedException ProductError) {
			JOptionPane.showMessageDialog(this, "Non è stato selezionato nessun prodotto.", "Errore", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	}
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
    
    private JRadioButton creaRadioButton(String testo) {
    	JRadioButton radioButton = new JRadioButton(testo);
    	radioButton.setForeground(nero);
    	radioButton.setFont(font);
    	radioButton.setBackground(Color.white);
    	radioButton.setActionCommand(testo);
    	
    	return radioButton;
    }
    
    private JTextArea creaTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setForeground(nero);
		textArea.setBackground(Color.white);
		textArea.setLineWrap(true);
		
		return textArea;
    }
    
    private JTextField creaTextField() {
    	JTextField textField = new JTextField(1);
    	textField.setFont(font);
    	textField.setForeground(nero);
    	textField.setBackground(Color.white);
    	
    	return textField;
    }
    
    private void aggiungiAlPannelloDialog(JPanel pannello, JLabel labelProdotto, JLabel labelConsegna, JScrollPane pannelloConsegna, JLabel labelTipologia, JRadioButton regalo, JRadioButton vendita, JRadioButton scambio, JLabel labelPrezzo) {
		GroupLayout layout = new GroupLayout(pannello);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
		GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
		gruppoVerticale
        .addComponent(labelProdotto)
        .addComponent(pannelloProdotti)
        .addComponent(labelConsegna)
        .addComponent(pannelloConsegna)
        .addComponent(labelTipologia)
        .addGroup(
        	layout.createParallelGroup()
		   .addComponent(regalo)
		   .addComponent(scambio)
		   .addComponent(vendita)
    		)
        .addComponent(labelPrezzo)
        .addComponent(textFieldPrezzo, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Integer.MAX_VALUE);
   
		gruppoOrizzontale
	       .addComponent(labelProdotto)
	        .addComponent(pannelloProdotti)
	        .addComponent(labelConsegna)
	        .addComponent(pannelloConsegna)
	        .addGroup(
	        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	        		.addGap(0, 0, Integer.MAX_VALUE)
	    	        .addComponent(labelTipologia)
	    	        .addGroup(
	    				layout.createSequentialGroup()
	    			   .addComponent(regalo)
	    			   .addComponent(scambio)
	    			   .addComponent(vendita)
	    	    		)
	    	        .addGap(0, 0, Integer.MAX_VALUE)
        		)
	        .addComponent(labelPrezzo)
	        .addComponent(textFieldPrezzo);
        
        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        pannello.setLayout(layout);
    }
	
}
