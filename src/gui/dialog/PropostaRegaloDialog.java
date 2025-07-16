package gui.dialog;

import session.SessionManager;

import javax.swing.*;

import dao.annuncio.AnnuncioRegaloDAOPostgre;
import dao.proposta.PropostaRegaloDAOPostgre;

import java.awt.*;
import entity.CardEntity;

public class PropostaRegaloDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public PropostaRegaloDialog(Frame owner, CardEntity card) {
        super(owner, "Richiesta Regalo", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 200);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Scrivi un messaggio al proprietario dell'annuncio:"), BorderLayout.NORTH);
        JTextArea messaggioArea = new JTextArea(5, 30);
        panel.add(new JScrollPane(messaggioArea), BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        JButton inviaButton = new JButton("Invia richiesta");
        inviaButton.addActionListener(e -> {
            String messaggio = messaggioArea.getText().trim();
            String username = SessionManager.getUsernameLoggato();

            if (messaggio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un messaggio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer idAnnuncio = AnnuncioRegaloDAOPostgre.getIdAnnuncioByProdottoId(card.getProdotto_id());
            if (idAnnuncio != null) {
                // Verifica se la proposta esiste già
                boolean esiste = PropostaRegaloDAOPostgre.propostaEsistente(idAnnuncio, username);
                if (esiste) {
                    JOptionPane.showMessageDialog(this, "Hai già inviato una proposta per questo annuncio.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean success = PropostaRegaloDAOPostgre.inserisciProposta(idAnnuncio, username, messaggio);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Richiesta inviata con successo.");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore durante l'invio della proposta.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Errore: annuncio regalo non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inviaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
