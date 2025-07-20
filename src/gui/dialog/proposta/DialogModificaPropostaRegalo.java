package gui.dialog.proposta;

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
import dto.proposte.PropostaRegaloDTO;
import exception.MessageNotFoundException;
import gui.dialog.FinestraDialog;

public class DialogModificaPropostaRegalo extends FinestraDialog {
	
	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	private PropostaRegaloDTO proposta;
	
	private JTextArea textAreaMessaggio;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	public DialogModificaPropostaRegalo(JFrame framePadre, ControllerApp controller, PropostaRegaloDTO proposta) {
		super(framePadre, "Modifica proposta regalo per " + proposta.getAnnuncio().getProdotto(), controller);
		
		this.controller = controller;
		this.proposta = proposta;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
		textAreaMessaggio.setText(getMessaggioProposta());
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelMessaggio = creaLabel("Modifica messaggio");
    	textAreaMessaggio = creaTextArea();
    	
    	JScrollPane pannelloConsegna = new JScrollPane(textAreaMessaggio);
    	
    	aggiungiAlPannelloDialog(pannello, labelMessaggio, pannelloConsegna);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Modifica Messaggio", nero);
		
		bottone.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	bottoneInterazioneClick();
            }
		});
		
		return bottone;
	}
	
	private String getMessaggioProposta() {
		try {
			return proposta.getMessaggio();
		} catch (MessageNotFoundException MessageNotFoundError) {
			return new String();
		}
	}
	
	private void bottoneInterazioneClick() {
		try {
			String messaggio = textAreaMessaggio.getText();
			
			controller.modificaPropostaRegalo(proposta, messaggio);
			
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
		JTextArea textArea = new JTextArea(getMessaggioProposta());
		textArea.setFont(font);
		textArea.setForeground(nero);
		textArea.setBackground(Color.white);
		textArea.setText(getMessaggioProposta());
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
