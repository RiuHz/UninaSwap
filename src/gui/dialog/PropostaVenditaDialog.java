package gui.dialog;

import session.SessionManager;

import javax.swing.*;

import dao.annuncio.AnnuncioVenditaDAOPostgre;
import dao.proposta.PropostaVenditaDAOPostgre;

import java.awt.*;
import entity.CardEntity;

public class PropostaVenditaDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public PropostaVenditaDialog(Frame owner, CardEntity card) {
        super(owner, "Proposta di Acquisto", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 150);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JLabel label = new JLabel("Inserisci la tua offerta (€):");
        JTextField propostaField = new JTextField();

        panel.add(label);
        panel.add(propostaField);
        add(panel, BorderLayout.CENTER);

        JButton inviaButton = new JButton("Invia proposta");
        inviaButton.addActionListener(e -> {
            try {
                double proposta = Double.parseDouble(propostaField.getText());
                if (proposta <= 0) {
                    JOptionPane.showMessageDialog(this, "La proposta deve essere maggiore di 0.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String username = SessionManager.getUsernameLoggato();
                Integer idAnnuncio = AnnuncioVenditaDAOPostgre.getIdAnnuncioByProdottoId(card.getProdotto_id());

                if (idAnnuncio != null) {
                    if (PropostaVenditaDAOPostgre.propostaEsistente(idAnnuncio, username)) {
                        JOptionPane.showMessageDialog(this, "Hai già inviato una proposta per questo annuncio.", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean inserita = PropostaVenditaDAOPostgre.inserisciProposta(idAnnuncio, username, proposta);
                        if (inserita) {
                            JOptionPane.showMessageDialog(this, "Proposta inviata con successo.");
                            dispose(); // chiudi solo se ok
                        } else {
                            JOptionPane.showMessageDialog(this, "Errore durante l'inserimento della proposta.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: annuncio di vendita non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci un valore numerico valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inviaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
