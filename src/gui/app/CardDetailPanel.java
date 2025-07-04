package gui.app;

import javax.swing.*;
import java.awt.*;

import entity.CardEntity;
import tipoAnnuncio.TipoAnnuncioDAOPostgre;
import aggiornaStatoAnnuncio.AggiornaStatoAnnuncioDAOPostgre;
import dialog.PropostaRegaloDialog;
import dialog.PropostaScambioDialog;
import dialog.PropostaVenditaDialog;
import session.SessionManager;
import proposta.*;

public class CardDetailPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public CardDetailPanel(CardEntity card, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel imageLabel = new JLabel();
        if (card.getImageIcon() != null) {
            Image scaled = card.getImageIcon().getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setText("Nessuna immagine");
        }
        imageLabel.setVerticalAlignment(SwingConstants.TOP);
        add(imageLabel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        infoPanel.add(new JLabel("Nome: " + card.getNomeProdotto()));
        infoPanel.add(new JLabel("Descrizione: " + card.getDescrizione()));
        infoPanel.add(new JLabel("Categoria: " + card.getCategoria()));
        infoPanel.add(new JLabel("Consegna: " + (card.getConsegna() != null ? card.getConsegna() : "Nessuna")));

        JLabel statoLabel = new JLabel("Stato: " + card.getTipo());
        infoPanel.add(statoLabel);
        infoPanel.add(Box.createVerticalStrut(20));

        add(infoPanel, BorderLayout.CENTER);

        String tipo = new TipoAnnuncioDAOPostgre().getTipoByProdottoId(card.getProdotto_id());
        String statoAnnuncio = card.getTipo();
        String proprietarioAnnuncio = card.getUsername();
        String usernameCorrente = SessionManager.getInstance().getUsernameLoggato();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel resultLabel = new JLabel();


        final JButton[] actionButton = new JButton[1];
        final JButton[] propostaButton = new JButton[1];


        if (usernameCorrente != null && !usernameCorrente.equalsIgnoreCase(proprietarioAnnuncio)) {
            if ("ATTIVO".equalsIgnoreCase(statoAnnuncio)) {
            	if ("VENDITA".equalsIgnoreCase(tipo)) {
            	    actionButton[0] = new JButton("Compra");
            	    propostaButton[0] = new JButton("Fai proposta di acquisto");

            	    propostaButton[0].addActionListener(e -> new PropostaVenditaDialog(null, card).setVisible(true));
            	    //la dialog non ha frame padre in questo caso quindi non viene centrata, ha anche altre perk ma non so quale sia la finestra principale poi se vuoi vedi tu

            	    actionButton[0].addActionListener(e -> {
            	        AggiornaStatoAnnuncioDAOPostgre.aggiornaStato("Annuncio_Vendita", card.getProdotto_id(), "Venduto");
            	        statoLabel.setText("Stato: Venduto");
            	        resultLabel.setText("Acquisto effettuato");
            	        actionButton[0].setEnabled(false);
            	        propostaButton[0].setEnabled(false);
            	    });

            	    bottomPanel.add(propostaButton[0]);
            	}
            	else if ("REGALO".equalsIgnoreCase(tipo)) {
                    actionButton[0] = new JButton("Prendi regalo");
                    propostaButton[0] = new JButton("Richiedi regalo");

                    propostaButton[0].addActionListener(e -> new PropostaRegaloDialog(null, card).setVisible(true));

                    actionButton[0].addActionListener(e -> {
                        AggiornaStatoAnnuncioDAOPostgre.aggiornaStato("Annuncio_Regalo", card.getProdotto_id(), "Regalato");
                        statoLabel.setText("Stato: Regalato");
                        resultLabel.setText("Regalo richiesto");
                        actionButton[0].setEnabled(false);
                        propostaButton[0].setEnabled(false);
                    });

                    bottomPanel.add(propostaButton[0]);

                }   else if ("SCAMBIO".equalsIgnoreCase(tipo)) {
                    actionButton[0] = new JButton("Scambia");
                    propostaButton[0] = new JButton("Proponi scambio");

                    propostaButton[0].addActionListener(e -> new PropostaScambioDialog(null, card).setVisible(true));

                    actionButton[0].addActionListener(e -> {
                        AggiornaStatoAnnuncioDAOPostgre.aggiornaStato("Annuncio_Scambio", card.getProdotto_id(), "Scambiato");
                        statoLabel.setText("Stato: Scambiato");
                        resultLabel.setText("Richiesta di scambio inviata");
                        actionButton[0].setEnabled(false);
                        propostaButton[0].setEnabled(false);
                    });

                    bottomPanel.add(propostaButton[0]);
                }

            } else {
                resultLabel.setText("Prodotto non disponibile");
            }
        } else if (usernameCorrente != null && usernameCorrente.equalsIgnoreCase(proprietarioAnnuncio)) {
            resultLabel.setText("È un tuo annuncio");
        }

        JButton backButton = new JButton("Torna Indietro");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Gallery"));
        bottomPanel.add(backButton);

        if (actionButton[0] != null) {
            bottomPanel.add(actionButton[0]);
        }
        bottomPanel.add(resultLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
