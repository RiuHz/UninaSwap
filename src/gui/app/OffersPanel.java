package gui.app;

import controller.AppController;
import controller.OffertaController;
import entity.OffertaEntity;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import session.SessionManager;

public class OffersPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel offertaListPanel;

    public OffersPanel(OffertaController controllerOne,AppController controller) {
        setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Offerte Ricevute");
        titolo.setFont(new Font("Arial", Font.BOLD, 18));
        titolo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titolo, BorderLayout.NORTH);

        // Panel scrollabile
        offertaListPanel = new JPanel();
        offertaListPanel.setLayout(new BoxLayout(offertaListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(offertaListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Top panel con bottone indietro
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("← Torna indietro");
        backButton.addActionListener(e -> {
            controllerOne.tornaAllaGallery();
        });
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Caricamento delle offerte (dallo username attivo)
        String username = SessionManager.getUsernameLoggato();
        System.out.println(username);
        try {
            List<OffertaEntity> offerte = controllerOne.getOfferteRicevute();

            mostraOfferte(offerte);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle offerte: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostraOfferte(List<OffertaEntity> offerte) {
        offertaListPanel.removeAll();

        if (offerte.isEmpty()) {
            JLabel vuoto = new JLabel("Nessuna offerta ricevuta.");
            vuoto.setAlignmentX(Component.CENTER_ALIGNMENT);
            offertaListPanel.add(vuoto);
        } else {
            for (OffertaEntity offerta : offerte) {
                JPanel card = creaCard(offerta);
                offertaListPanel.add(card);
                offertaListPanel.add(Box.createVerticalStrut(10));
            }
        }

        offertaListPanel.revalidate();
        offertaListPanel.repaint();
    }

    private JPanel creaCard(OffertaEntity offerta) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Immagine
        if (offerta.getImmagine() != null) {
            ImageIcon icon = new ImageIcon(offerta.getImmagine());
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.add(imgLabel, BorderLayout.WEST);
        }

        // Dettagli
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerPanel.add(new JLabel("Prodotto: " + offerta.getNomeProdotto()));
        centerPanel.add(new JLabel("Tipo: " + offerta.getTipoOfferta()));
        centerPanel.add(new JLabel("Proponente: " + offerta.getUsernameProponente()));
        centerPanel.add(new JLabel("Stato: " + offerta.getStato()));

        if (offerta.getTipoOfferta().equalsIgnoreCase("Vendita") && offerta.getProposta() != null) {
            centerPanel.add(new JLabel("Offerta €: " + offerta.getProposta()));
        } else if (offerta.getTipoOfferta().equalsIgnoreCase("Regalo") && offerta.getMessaggio() != null) {
            centerPanel.add(new JLabel("Messaggio: " + offerta.getMessaggio()));
        }

        card.add(centerPanel, BorderLayout.CENTER);

        return card;
    }
}
