package gui.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import card.CardDAOPostgre;
import controller.AppController;
import dialog.AggiungiProdottoDialog;
import entity.CardEntity;
import gui.utils.WrapLayout;
import dao.category.CategoryDAOPostgre;

public class ProductGalleryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel cardsContainer;
    private final AppController controller;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    public ProductGalleryPanel(AppController controller, JPanel mainPanel, CardLayout cardLayout) {
        this.controller = controller;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());

        // PANNELLO TOP CON FILTRI
        JPanel topPanel = new JPanel(new BorderLayout());

        JButton filtriButton = new JButton("Filtri");
        filtriButton.addActionListener(this::mostraFiltroPopup);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(filtriButton);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JButton aggiungiButton = new JButton("Aggiungi Prodotto");
        aggiungiButton.addActionListener(e -> {
            AggiungiProdottoDialog dialog = new AggiungiProdottoDialog(SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);
            refreshCards();
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(aggiungiButton);
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // CONTAINER PER LE CARD
        cardsContainer = new JPanel();
        cardsContainer.setLayout(new WrapLayout(FlowLayout.CENTER, 20, 20));
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(cardsContainer, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(
            wrapper,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadCards();
    }

    private void mostraFiltroPopup(ActionEvent e) {
    	JDialog filtroDialog = new JDialog(SwingUtilities.getWindowAncestor(this));
    	filtroDialog.setTitle("Filtri");
    	filtroDialog.setModal(true);

        filtroDialog.setLayout(new BorderLayout());

        JPanel contenuto = new JPanel();
        contenuto.setLayout(new BoxLayout(contenuto, BoxLayout.Y_AXIS));

        // TIPOLOGIA
        JLabel tipologiaLabel = new JLabel("Tipologia:");
        JCheckBox venditaCheck = new JCheckBox("Vendita");
        JCheckBox scambioCheck = new JCheckBox("Scambio");
        JCheckBox regaloCheck = new JCheckBox("Regalo");

        contenuto.add(tipologiaLabel);
        contenuto.add(venditaCheck);
        contenuto.add(scambioCheck);
        contenuto.add(regaloCheck);

        // CATEGORIA
        JLabel categoriaLabel = new JLabel("Categoria:");
        contenuto.add(Box.createVerticalStrut(10));
        contenuto.add(categoriaLabel);

        List<JCheckBox> categoriaCheckboxes = new ArrayList<>();
        for (String categoria : new CategoryDAOPostgre().getAllCategoryNames()) {
            JCheckBox check = new JCheckBox(categoria);
            categoriaCheckboxes.add(check);
            contenuto.add(check);
        }

        JButton applicaButton = new JButton("Applica Filtri");
        applicaButton.addActionListener(ae -> {
            List<String> tipologie = new ArrayList<>();
            if (venditaCheck.isSelected()) tipologie.add("Vendita");
            if (scambioCheck.isSelected()) tipologie.add("Scambio");
            if (regaloCheck.isSelected()) tipologie.add("Regalo");

            List<String> categorie = new ArrayList<>();
            for (JCheckBox cb : categoriaCheckboxes) {
                if (cb.isSelected()) categorie.add(cb.getText());
            }

            filtraProdotti(categorie, tipologie);
            filtroDialog.dispose();
        });

        filtroDialog.add(new JScrollPane(contenuto), BorderLayout.CENTER);
        filtroDialog.add(applicaButton, BorderLayout.SOUTH);
        filtroDialog.setSize(300, 400);
        filtroDialog.setLocationRelativeTo(this);
        filtroDialog.setVisible(true);
    }

    private void loadCards() {
        cardsContainer.removeAll();

        CardDAOPostgre dao = new CardDAOPostgre();
        List<CardEntity> cards = dao.getLast30Cards();

        for (CardEntity card : cards) {
            CardPanel panel = new CardPanel(card, mainPanel, cardLayout);
            cardsContainer.add(panel);
        }

        revalidate();
        repaint();
    }

    private void refreshCards() {
        loadCards();
    }

    private void filtraProdotti(List<String> categorie, List<String> tipologie) {
        cardsContainer.removeAll();

        CardDAOPostgre dao = new CardDAOPostgre();
        List<CardEntity> cards = dao.getFilteredCards(categorie, tipologie);

        for (CardEntity card : cards) {
            CardPanel panel = new CardPanel(card, mainPanel, cardLayout);
            cardsContainer.add(panel);
        }

        cardsContainer.revalidate();
        cardsContainer.repaint();
    }



}
