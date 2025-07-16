package gui.dialog;

import controller.AggiungiProdottoController;
import controller.AppController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class AggiungiProdottoDialog extends JDialog implements DialogInterface {

    private static final long serialVersionUID = 1L;
    
    private AppController controller;

    private JTextField nomeField;
    private JTextArea descrizioneArea;
    private JComboBox<Categoria> categoriaBox;
    private JComboBox<String> tipoAnnuncioBox;
    private JTextField consegnaField;
    private JTextField prezzoField;
    private JLabel immagineLabel;
    private byte[] immagineBytes;

    public AggiungiProdottoDialog(JFrame frameOwner, AppController controller) {
    	super(frameOwner, "Aggiungi Prodotto", true);
    	
    	this.controller = controller;

    	setWindowSettings(frameOwner);
    	createComponents();
    	
    	
    	
    	
    	
    	

        JPanel formPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        nomeField = new JTextField();
        descrizioneArea = new JTextArea(3, 20);

        categoriaBox = new JComboBox<>();
        List<Categoria> categorie = new CategoryDAOPostgre().getAllCategorie();
        for (Categoria c : categorie) {
            categoriaBox.addItem(c); // visualizze il nome grazie al toString
        }

        tipoAnnuncioBox = new JComboBox<>(new String[]{"Nessuno", "Vendita", "Scambio", "Regalo"});
        consegnaField = new JTextField();
        prezzoField = new JTextField();

        immagineLabel = new JLabel("Nessuna immagine selezionata");
        JButton scegliImmagineButton = new JButton("Seleziona Immagine");
        scegliImmagineButton.addActionListener(e -> selezionaImmagine());

        formPanel.add(new JLabel("Nome Prodotto:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Descrizione:"));
        formPanel.add(new JScrollPane(descrizioneArea));
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoriaBox);
        formPanel.add(new JLabel("Tipo Annuncio:"));
        formPanel.add(tipoAnnuncioBox);
        formPanel.add(new JLabel("Consegna:"));
        formPanel.add(consegnaField);
        formPanel.add(new JLabel("Prezzo (solo per vendita):"));
        formPanel.add(prezzoField);
        formPanel.add(immagineLabel);
        formPanel.add(scegliImmagineButton);

        JButton aggiungiButton = new JButton("Aggiungi");
        aggiungiButton.addActionListener(e -> aggiungiProdotto());

        add(formPanel, BorderLayout.CENTER);
        add(aggiungiButton, BorderLayout.SOUTH);
    }
    
	@Override
	public void showDialog() {
		setVisible(true);
	}

	@Override
	public void hideDialog() {
		setVisible(false);
	}
	
	private void setWindowSettings(JFrame frameOwner) {
        setSize(400, 600);
        setLocationRelativeTo(frameOwner);
        setResizable(false);
	}
	
	private void createComponents() {
		getContentPane().setLayout(new GridLayout(0, 1, 5, 5));
	}
	
	
	
	
	

    private void selezionaImmagine() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                immagineBytes = baos.toByteArray();
                baos.close();
                immagineLabel.setText("Immagine selezionata: " + file.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'immagine.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void aggiungiProdotto() {
        String nome = nomeField.getText();
        String descrizione = descrizioneArea.getText();
        Categoria categoria = (Categoria) categoriaBox.getSelectedItem();
        String tipoAnnuncio = (String) tipoAnnuncioBox.getSelectedItem();
        String consegna = consegnaField.getText();
        Double prezzo = null;

        if (!prezzoField.getText().isBlank()) {
            try {
                prezzo = Double.parseDouble(prezzoField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Prezzo non valido (non è un numero).", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String username = SessionManager.getUsernameLoggato();
        AggiungiProdottoController controller = new AggiungiProdottoController();

        try {
            controller.aggiungiProdotto(
                nome,
                descrizione,
                immagineBytes,
                username,
                categoria.getId(),
                tipoAnnuncio,
                consegna,
                prezzo
            );

            JOptionPane.showMessageDialog(this, "Prodotto aggiunto con successo!");
            dispose();

        } catch (SQLException SQLError) {
            JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}
