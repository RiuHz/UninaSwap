package gui.dialog.richiesta;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ControllerApp;
import dto.annunci.AnnuncioVenditaDTO;
import gui.dialog.FinestraDialog;

public class DialogRichiediVendita extends FinestraDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	private AnnuncioVenditaDTO annuncio;
	
	private JTextField textFieldPrezzo;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	public DialogRichiediVendita(JFrame framePadre, ControllerApp controller, AnnuncioVenditaDTO annuncio) {
		super(framePadre, "Proponi un prezzo per " + annuncio.getProdotto(), controller);
		
		this.controller = controller;
		this.annuncio = annuncio;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
		textFieldPrezzo.setText("");
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelPrezzo = creaLabel("Proponi un prezzo");
    	textFieldPrezzo = creaTextField();
    	
    	aggiungiAlPannelloDialog(pannello, labelPrezzo, textFieldPrezzo);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Proponi Prezzo", nero);
		
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
			double prezzo = Double.valueOf(textFieldPrezzo.getText());
			
			controller.creaPropostaVendita(annuncio, prezzo);
			
			nascondiDialog();
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	} catch (NumberFormatException NumberFormatoError) {
			JOptionPane.showMessageDialog(this, "Non è stato inserito un prezzo valido.", "Errore", JOptionPane.ERROR_MESSAGE);
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
    
    private JTextField creaTextField() {
    	JTextField textField = new JTextField(1);
    	textField.setFont(font);
    	textField.setForeground(nero);
    	textField.setBackground(Color.white);
    	
    	return textField;
    }
    
    private void aggiungiAlPannelloDialog(JPanel pannello, JComponent ...componenti) {
		GroupLayout layout = new GroupLayout(pannello);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
		GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup();
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
        
		for (JComponent componente : componenti) {
			gruppoVerticale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
			gruppoOrizzontale.addComponent(componente);
		}
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
		
        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        pannello.setLayout(layout);
    }
	
}
