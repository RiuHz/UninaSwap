package dialog;

import dao.category.CategoryDAOPostgre;
import entity.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FiltroProdottiDialog extends JDialog {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final List<JCheckBox> tipoCheckBoxList = new ArrayList<>();
    private final List<JCheckBox> categoriaCheckBoxList = new ArrayList<>();
    private final JButton applicaButton;

    public FiltroProdottiDialog(Frame owner, ActionListener filtroListener) {
        super(owner, "Filtra Prodotti", true);
        setLayout(new BorderLayout(10, 10));
        setSize(300, 500);
        setLocationRelativeTo(owner);

        JPanel filtroPanel = new JPanel();
        filtroPanel.setLayout(new BoxLayout(filtroPanel, BoxLayout.Y_AXIS));

        // Tipologia
        filtroPanel.add(new JLabel("Tipologia Annuncio"));
        String[] tipi = {"Vendita", "Scambio", "Regalo"};
        for (String tipo : tipi) {
            JCheckBox checkBox = new JCheckBox(tipo);
            tipoCheckBoxList.add(checkBox);
            filtroPanel.add(checkBox);
        }

        filtroPanel.add(Box.createVerticalStrut(10));

        // Categoria
        filtroPanel.add(new JLabel("Categoria"));
        CategoryDAOPostgre categoriaDAO = new CategoryDAOPostgre();
        List<Categoria> categorie = categoriaDAO.getAllCategorie();

        for (Categoria c : categorie) {
            JCheckBox checkBox = new JCheckBox(c.getNome());
            checkBox.setActionCommand(String.valueOf(c.getId()));
            categoriaCheckBoxList.add(checkBox);
            filtroPanel.add(checkBox);
        }

        // Bottone Applica
        applicaButton = new JButton("Applica Filtri");
        applicaButton.addActionListener(filtroListener);
        filtroPanel.add(Box.createVerticalStrut(10));
        filtroPanel.add(applicaButton);

        JScrollPane scrollPane = new JScrollPane(filtroPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public List<String> getTipologieSelezionate() {
        List<String> selezionate = new ArrayList<>();
        for (JCheckBox cb : tipoCheckBoxList) {
            if (cb.isSelected()) {
                selezionate.add(cb.getText());
            }
        }
        return selezionate;
    }

    public List<Integer> getCategorieSelezionate() {
        List<Integer> selezionate = new ArrayList<>();
        for (JCheckBox cb : categoriaCheckBoxList) {
            if (cb.isSelected()) {
                selezionate.add(Integer.parseInt(cb.getActionCommand()));
            }
        }
        return selezionate;
    }
}
