package gui.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.AppController;
import card.CardDAOPostgre;
import entity.CardEntity;
import gui.utils.WrapLayout; // Importa il layout personalizzato

public class ProductGalleryPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel cardsContainer;

    public ProductGalleryPanel(AppController controller) {
        setLayout(new BorderLayout());

        cardsContainer = new JPanel();
        cardsContainer.setLayout(new WrapLayout(FlowLayout.CENTER, 20, 20));
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(cardsContainer,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        scrollPane.getViewport().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = scrollPane.getViewport().getWidth();
                cardsContainer.setPreferredSize(new Dimension(width, cardsContainer.getPreferredSize().height));
                cardsContainer.revalidate();
            }
        });

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);


        add(scrollPane, BorderLayout.CENTER);

        loadCards();
    }

    private void loadCards() {
        CardDAOPostgre dao = new CardDAOPostgre();
        List<CardEntity> cards = dao.getLast30Cards();

        for (CardEntity card : cards) {
            cardsContainer.add(new CardPanel(card));
        }

        revalidate();
        repaint();
    }
}
