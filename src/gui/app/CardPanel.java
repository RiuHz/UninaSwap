package gui.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entity.CardEntity;

public class CardPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public CardPanel(CardEntity card, JPanel mainPanel, CardLayout cardLayout) {
        setPreferredSize(new Dimension(200, 300));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Panel per l'immagine
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(200, 150));
        imagePanel.setBackground(Color.LIGHT_GRAY);

        if (card.getImageIcon() != null) {
            JLabel imageLabel = new JLabel();
            ImageIcon icon = card.getImageIcon();

            Image scaled = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);

            imagePanel.add(imageLabel, BorderLayout.CENTER);
        } else {
            JLabel noImage = new JLabel("Nessuna immagine", SwingConstants.CENTER);
            imagePanel.add(noImage, BorderLayout.CENTER);
        }

        add(imagePanel, BorderLayout.NORTH);

        // Panel info testuali
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        infoPanel.add(new JLabel("Nome: " + card.getNomeProdotto()));
        infoPanel.add(new JLabel("Descrizione: " + card.getDescrizione()));
        infoPanel.add(new JLabel("Categoria: " + card.getCategoria()));
        infoPanel.add(new JLabel("Tipo: " + (card.getTipo() != null ? card.getTipo() : "Nessuno")));
        infoPanel.add(new JLabel("Consegna: " + (card.getConsegna() != null ? card.getConsegna() : "null")));

        add(infoPanel, BorderLayout.CENTER);

        // click per mostrare il dettaglio
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardDetailPanel detailPanel = new CardDetailPanel(card, mainPanel, cardLayout);
                mainPanel.add(detailPanel, "detail");
                cardLayout.show(mainPanel, "detail");
            }
        });

    }
}
