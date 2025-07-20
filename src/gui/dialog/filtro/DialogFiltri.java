package gui.dialog.filtro;

import javax.swing.*;

import controller.ControllerApp;
import dto.CategoriaDTO;
import gui.dialog.FinestraDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class DialogFiltri extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);
	
	private final ArrayList<JCheckBox> CheckBoxTipologia = new ArrayList<>();
    private final ArrayList<JCheckBox> CheckBoxCategoria = new ArrayList<>();
    
	private final String[] tipiologiaAnnunci = {"Regalo", "Vendita", "Scambio"};
    
    public DialogFiltri(JFrame frameOwner, ControllerApp controller) {
    	super(frameOwner, "Filtra Prodotto", controller);
    	
    	this.controller = controller;

    	creaComponenti(controller);
    }
    
    @Override
    public void aggiornaDialog() {}
    
    private ArrayList<CategoriaDTO> getListaCategorie() {
    	ArrayList<CategoriaDTO> lista = new ArrayList<CategoriaDTO>();
    	
    	try {
    		lista = controller.getListaCategorie();
    	} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	return lista;
    }
    
    private ArrayList<String> getTipologieSelezionate() {
        ArrayList<String> selezionate = new ArrayList<>();
        
        for (JCheckBox check : CheckBoxTipologia) {
            if (check.isSelected()) {
                selezionate.add(check.getText());
            }
        }
        
        return selezionate;
    }

    private ArrayList<Integer> getCategorieSelezionate() {
        ArrayList<Integer> selezionate = new ArrayList<>();
        
        for (JCheckBox check : CheckBoxCategoria) {
            if (check.isSelected()) {
                selezionate.add(Integer.parseInt(check.getActionCommand()));
            }
        }
        return selezionate;
    }
    
	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelTipologia = creaLabel("Tipologia Annuncio");
    	
	    for (String tipologia : tipiologiaAnnunci) {
			JCheckBox checkBox = createCheckBox(tipologia);
			CheckBoxTipologia.add(checkBox);
	    }
	    
    	JLabel labelCategoria = creaLabel("Categoria Prodotto");
    	
		ArrayList<CategoriaDTO> listaCategorie = getListaCategorie();
    	
		for (CategoriaDTO categoria : listaCategorie) {
		    JCheckBox checkBox = createCheckBox(categoria.getNome());
		    checkBox.setActionCommand(String.valueOf(categoria.getId()));		
		    CheckBoxCategoria.add(checkBox);
		}
		
		aggiungiAlPannelloDialog(pannello, labelTipologia, labelCategoria);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Applica Filtri", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	bottoneInterazioneClick();
            }
		});
		
		return bottone;
	}
	
	private void bottoneInterazioneClick() {
    	ArrayList<String> tipologieSelezionate = getTipologieSelezionate();
    	ArrayList<Integer> categorieSelezionate = getCategorieSelezionate();
    	
    	try {
        	if (tipologieSelezionate.isEmpty() && categorieSelezionate.isEmpty())
        		controller.aggiornaAnnunci();
        	else if (tipologieSelezionate.isEmpty())
        		controller.aggiornaAnnunciPerCategoria(categorieSelezionate);
        	else if (categorieSelezionate.isEmpty())
        		controller.aggiornaAnnunciPerTipologia(tipologieSelezionate);
        	else
        		controller.aggiornaAnnunci(tipologieSelezionate, categorieSelezionate);
        	
        	nascondiDialog();
    	} catch(SQLException SQLError) {
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
    
    private JCheckBox createCheckBox(String testo) {
    	JCheckBox checkBox = new JCheckBox(testo);
    	checkBox.setFont(font);
    	checkBox.setForeground(nero);
    	checkBox.setBackground(Color.white);
    	
    	return checkBox;
    }
    
    private void aggiungiAlPannelloDialog(JPanel pannello, JLabel labelTipologia, JLabel labelCategoria) {
        GroupLayout layout = new GroupLayout(pannello);
    	
    	layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup();
        
        gruppoOrizzontale.addComponent(labelTipologia);
        gruppoVerticale.addComponent(labelTipologia);
        
        for (JCheckBox checkBox : CheckBoxTipologia) {
        	gruppoOrizzontale.addComponent(checkBox);
        	gruppoVerticale.addComponent(checkBox);
        }
        
        gruppoOrizzontale.addComponent(labelCategoria);
        gruppoVerticale.addComponent(labelCategoria);
        
        for (JCheckBox checkBox : CheckBoxCategoria) {
        	gruppoOrizzontale.addComponent(checkBox);
        	gruppoVerticale.addComponent(checkBox);
        }
        
        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        pannello.setLayout(layout);
    }

}
