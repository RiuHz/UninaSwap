package dialog;

import controller.AggiungiProdottoController;
import dao.category.CategoryDAOPostgre;
import entity.Categoria;
import exceptionInserimento.*;
import session.SessionManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class AggiungiProdottoDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private JTextField nomeField;
    private JTextArea descrizioneArea;
    private JComboBox<Categoria> categoriaBox;
    private JComboBox<String> tipoAnnuncioBox;
    private JTextField consegnaField;
    private JTextField prezzoField;
    private JLabel immagineLabel;
    private byte[] immagineBytes;

    public AggiungiProdottoDialog(Window owner) {
    	super(owner, "Aggiungi Prodotto", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10, 10));
        setSize(500, 600);
        setLocationRelativeTo(owner);

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
                JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'immagine.");
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

        } catch (NomeProdottoNonValidoException e) {
            JOptionPane.showMessageDialog(this, "Errore: nome non valido.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (DescrizioneNonValidaException e) {
            JOptionPane.showMessageDialog(this, "Errore: descrizione troppo corta.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (ImmagineNonValidaException e) {
            JOptionPane.showMessageDialog(this, "Errore: immagine non valida.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (CategoriaNonValidaException e) {
            JOptionPane.showMessageDialog(this, "Errore: categoria non valida.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (ConsegnaNonValidaException e) {
            JOptionPane.showMessageDialog(this, "Errore: consegna non valida.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (PrezzoNonValidoException e) {
            JOptionPane.showMessageDialog(this, "Errore: prezzo non valido.\n" + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore generico: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

}
