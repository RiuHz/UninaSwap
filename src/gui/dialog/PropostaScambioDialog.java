package gui.dialog;

import dao.annuncio.AnnuncioScambioDAOPostgre;
import dao.proposta.PropostaScambioDAOPostgre;
import daoOLD.product.ProductDAOPostgre;
import entity.Prodotto;
import session.SessionManager;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import entity.CardEntity;

public class PropostaScambioDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private final List<JCheckBox> checkboxes = new ArrayList<>();

    public PropostaScambioDialog(Frame owner, CardEntity card) {
        super(owner, "Proposta di Scambio", true);
        setLayout(new BorderLayout(10, 10));
        setSize(450, 300);
        setLocationRelativeTo(owner);

        String username = SessionManager.getUsernameLoggato();
        List<Prodotto> prodottiLiberi = new ProductDAOPostgre().getProdottiLiberiByUsername(username);

        if (prodottiLiberi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai prodotti disponibili per lo scambio.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

        for (Prodotto p : prodottiLiberi) {
            JCheckBox cb = new JCheckBox(p.getNome() + " - " + p.getDescrizione());
            cb.putClientProperty("id_prodotto", p.getId());
            checkboxes.add(cb);
            listaPanel.add(cb);
        }

        JScrollPane scrollPane = new JScrollPane(listaPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Seleziona i prodotti da offrire"));
        add(scrollPane, BorderLayout.CENTER);

        JButton inviaButton = new JButton("Invia proposta di scambio");
        inviaButton.addActionListener(e -> {
            List<Integer> prodottiSelezionati = new ArrayList<>();

            for (JCheckBox cb : checkboxes) {
                if (cb.isSelected()) {
                    prodottiSelezionati.add((Integer) cb.getClientProperty("id_prodotto"));
                }
            }

            if (prodottiSelezionati.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleziona almeno un prodotto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer idAnnuncio = AnnuncioScambioDAOPostgre.getIdAnnuncioByProdottoId(card.getProdotto_id());
            if (idAnnuncio != null) {
                if (PropostaScambioDAOPostgre.propostaEsistente(idAnnuncio, username)) {
                    JOptionPane.showMessageDialog(this, "Hai già inviato una proposta per questo annuncio.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean success = PropostaScambioDAOPostgre.inserisciProposta(idAnnuncio, username, prodottiSelezionati);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Proposta di scambio inviata con successo.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Errore durante l'inserimento della proposta.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Errore: annuncio scambio non trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
            }

            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inviaButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
