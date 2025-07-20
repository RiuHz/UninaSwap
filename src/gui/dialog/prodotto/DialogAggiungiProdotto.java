package gui.dialog.prodotto;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ControllerApp;
import dto.CategoriaDTO;
import exception.InvalidImageSelectedException;
import gui.dialog.FinestraDialog;

public class DialogAggiungiProdotto extends FinestraDialog {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JFrame framePadre;
	
	private JTextField textFieldNome;
	private JTextArea textAreaDescrizione = creaTextArea();
	private JComboBox<CategoriaDTO> comboBoxCategoria;
	private JButton bottoneImmagine;
	private byte[] immagine;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);
	
	public DialogAggiungiProdotto(JFrame framePadre, ControllerApp controller) {
		super(framePadre, "Aggiungi un Prodotto", controller);
		
		this.controller = controller;
		this.framePadre = framePadre;
		
		creaComponenti(controller);
	}
	
	@Override
	public void aggiornaDialog() {
		textFieldNome.setText("");
		textAreaDescrizione.setText("");
		comboBoxCategoria.setSelectedIndex(1);
		bottoneImmagine.setText("Seleziona un'Immagine");
	}

	@Override
	protected JPanel creaPannelloDialog() {
    	JPanel pannello = new JPanel();
    	pannello.setBackground(Color.white);
    	
    	JLabel labelNome = creaLabel("Nome");
    	textFieldNome = creaTextField();
    	
    	bottoneImmagine = new UnderlineButton("Seleziona un'Immagine", nero);
    	
    	bottoneImmagine.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			JFileChooser fileChooser = new JFileChooser();
    			fileChooser.setAcceptAllFileFilterUsed(false);
    			fileChooser.setFileFilter(new FileNameExtensionFilter("Immagini", ImageIO.getReaderFileSuffixes()));
    			
    			if (fileChooser.showOpenDialog(framePadre) == JFileChooser.APPROVE_OPTION) {
    				File file = fileChooser.getSelectedFile();
    				
    				try {
						immagine = Files.readAllBytes(file.toPath());
					} catch (IOException e1) {
						 JOptionPane.showMessageDialog(framePadre, "Errore nel caricamento dell'immagine.", "Errore", JOptionPane.ERROR_MESSAGE);
					}
    				
    				bottoneImmagine.setText(file.getName());
    			}
    		}
    	});
    	
    	JLabel labelDescrizione = creaLabel("Descrizione");
    	JScrollPane pannelloDescrizione = new JScrollPane(textAreaDescrizione);
    	
    	JLabel labelCategoria = creaLabel("Categoria");
    	comboBoxCategoria = creaComboBox(getListaCategorie());
    	
    	aggiungiAlPannelloDialog(pannello, labelNome, bottoneImmagine, labelDescrizione, pannelloDescrizione, labelCategoria);
    	
    	return pannello;
	}

	@Override
	protected JButton bottoneInterazione(ControllerApp controller) {
		JButton bottone = new UnderlineButton("Aggiungi Prodotto", nero);
		
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
			String nome = textFieldNome.getText();
			String descrizione = textAreaDescrizione.getText();
			CategoriaDTO categoria = (CategoriaDTO) comboBoxCategoria.getSelectedItem();
					
			controlloValiditaImmagine();
			
			controller.creaProdotto(nome, immagine, descrizione, categoria);
			
			nascondiDialog();
		} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0], "Errore", JOptionPane.ERROR_MESSAGE);
    	} catch (InvalidImageSelectedException ImageError) {
			JOptionPane.showMessageDialog(framePadre, "Errore nel caricamento dell'immagine.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void controlloValiditaImmagine() throws InvalidImageSelectedException {
		if (immagine == null)
			throw new InvalidImageSelectedException();
	}
	
    private ArrayList<CategoriaDTO> getListaCategorie() {
    	ArrayList<CategoriaDTO> listaCategoria = new ArrayList<CategoriaDTO>();
    	
    	try {
    		listaCategoria = controller.getListaCategorie();
    	} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	return listaCategoria;
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
    
    private JTextArea creaTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setForeground(nero);
		textArea.setBackground(Color.white);
		
		return textArea;
    }
    
    private JComboBox<CategoriaDTO> creaComboBox(ArrayList<CategoriaDTO> elementi) {
    	JComboBox<CategoriaDTO> comboBox = new JComboBox<CategoriaDTO>(elementi.toArray(new CategoriaDTO[0]));
    	comboBox.setFont(font);
    	comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	
    	return comboBox;
    }
    
    private void aggiungiAlPannelloDialog(JPanel pannello, JLabel labelNome, JButton bottoneImmagine, JLabel labelDescrizione, JScrollPane pannelloDescrizione, JLabel labelCategoria) {
		GroupLayout layout = new GroupLayout(pannello);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
		GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
        
		gruppoVerticale
		.addComponent(labelNome)
		.addComponent(textFieldNome, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
		.addComponent(bottoneImmagine)
		.addComponent(labelDescrizione)
		.addComponent(pannelloDescrizione)
		.addComponent(labelCategoria)
		.addComponent(comboBoxCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
		
		gruppoOrizzontale
		.addComponent(labelNome)
		.addComponent(textFieldNome)
		.addComponent(bottoneImmagine)
		.addComponent(labelDescrizione)
		.addComponent(pannelloDescrizione)
		.addComponent(labelCategoria)
		.addComponent(comboBoxCategoria);
		
		
		gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
		
        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        pannello.setLayout(layout);
    }

}
