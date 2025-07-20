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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.ControllerApp;
import dto.annunci.AnnuncioRegaloDTO;
import gui.dialog.FinestraDialog;

public class DialogRichiediRegalo extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	private AnnuncioRegaloDTO annuncio;
	
	private JTextArea textAreaMessaggio = creaTextArea();
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);
	
	public DialogRichiediRegalo(JFrame framePadre, ControllerApp controller, AnnuncioRegaloDTO annuncio) {
		super(framePadre, "Richiedi in Regalo" + annuncio.getProdotto(), controller);
		
		this.controller = controller;
		this.annuncio = annuncio;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
		textAreaMessaggio.setText("");
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelMessaggio = creaLabel("Lascia un messaggio");
    	JScrollPane pannelloMessaggio = new JScrollPane(textAreaMessaggio);
    	
    	aggiungiAlPannelloDialog(pannello, labelMessaggio, pannelloMessaggio);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Richiedi Regalo", nero);
		
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
			String messaggio = textAreaMessaggio.getText();
			
			controller.creaPropostaRegalo(annuncio, messaggio);
			
			nascondiDialog();
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
    
    private JTextArea creaTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setForeground(nero);
		textArea.setBackground(Color.white);
		textArea.setLineWrap(true);
		
		return textArea;
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
