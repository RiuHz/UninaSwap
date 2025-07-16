package gui.dialog;

import javax.swing.*;

import controller.AppController;
import dto.CategoriaDTO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FiltroDialog extends JDialog implements DialogInterface {

	private static final long serialVersionUID = 1L;
	
	private AppController controller;
	
	private final Font verdanaFont18 = new Font("Verdana", Font.PLAIN, 18);
	private final Font verdanaFont16 = new Font("Verdana", Font.PLAIN, 16);
	
	private final Color black = new Color(0x262626);

	private ArrayList<JCheckBox> CheckBoxTipologia = new ArrayList<>();
    private ArrayList<JCheckBox> CheckBoxCategoria = new ArrayList<>();
    
    public FiltroDialog(JFrame frameOwner, AppController controller) {
    	super(frameOwner, "Filtra Prodotto", true);
    	
    	this.controller = controller;
    	
    	setWindowSettings(frameOwner);
    	createComponents();

    }
    
	@Override
	public void showDialog() {
		setVisible(true);
	}

	@Override
	public void hideDialog() {
		setVisible(false);
	}
    
    private void setWindowSettings(JFrame frameOwner) {
        setSize(400, 600);
        setLocationRelativeTo(frameOwner);
        setResizable(false);
    }
    
    private void createComponents() {
        getContentPane().setLayout(new BorderLayout());
        
        JScrollPane scrollPanel = new JScrollPane(createSelectionPanel());
        
        JPanel buttonPanel = createButtonPanel();
        
        getContentPane().add(scrollPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createSelectionPanel() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	
    	panel.add(createLabel("Tipologia Annuncio", black));
    	
    	String[] tipiologiaAnnunci = {"Regalo", "Vendita", "Scambio"};
    	
	    for (String tipologia : tipiologiaAnnunci) {
	    	  
			JCheckBox checkBox = createCheckBox(tipologia, black);
			CheckBoxTipologia.add(checkBox);
			  
			panel.add(checkBox);
	    }
	    
	    panel.add(Box.createVerticalStrut(16));
    	
    	panel.add(createLabel("Categoria Prodotto", black));
    
		ArrayList<CategoriaDTO> listaCategorie = getCategorie();
		
		for (CategoriaDTO categoria : listaCategorie) {
		    JCheckBox checkBox = createCheckBox(categoria.getNome(), black);
		    checkBox.setActionCommand(String.valueOf(categoria.getId()));		
		    CheckBoxCategoria.add(checkBox);
		    panel.add(checkBox);
		}
    	
    	return panel;
    }
    
    private JPanel createButtonPanel() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
    	
    	JButton bottoneFiltri = new UnderlineButton("Applica Filtri", black);
    	
        bottoneFiltri.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
        		
        		// TODO Qui dovrei poi richiamare HomePanel e applicare i filtri, dovrebbe lui conservare la lista di prodotti mostrati
        		
            	for (String tipologia: getTipologieSelezionate())
            		System.out.println(tipologia);
            	
            	for (int categoria: getCategorieSelezionate())
            		System.out.println(categoria);
            	
            	hideDialog();
            }
        });
    	
    	panel.add(bottoneFiltri);
    	
    	return panel;
    }
    
    private ArrayList<String> getTipologieSelezionate() {
        ArrayList<String> selezionate = new ArrayList<>();
        
        for (JCheckBox check : CheckBoxTipologia) {
            if (check.isSelected()) {
                selezionate.add(check.getText());
            }
        }
        
        return selezionate;
    }

    private ArrayList<Integer> getCategorieSelezionate() {
        ArrayList<Integer> selezionate = new ArrayList<>();
        
        for (JCheckBox check : CheckBoxCategoria) {
            if (check.isSelected()) {
                selezionate.add(Integer.parseInt(check.getActionCommand()));
            }
        }
        return selezionate;
    }
    
    private ArrayList<CategoriaDTO> getCategorie() {
    	ArrayList<CategoriaDTO> lista = new ArrayList<CategoriaDTO>();
    	
    	try {
    		lista = controller.getCategorie();
    	} catch (SQLException SQLError) {
    		JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	return lista;
    }
    
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
    
    protected JLabel createLabel(String text, Color color) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont18);
    	label.setForeground(color);
        
        return label;
    }
    
    protected JCheckBox createCheckBox(String text, Color color) {
    	JCheckBox checkBox = new JCheckBox(text);
    	checkBox.setFont(verdanaFont16);
    	checkBox.setForeground(color);
    	
    	return checkBox;
    }
    
    protected class UnderlineButton extends JButton {

    	private static final long serialVersionUID = 1L;

    	public UnderlineButton(String text, Color color) {
    		super(text);

    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}

    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(verdanaFont18.getAttributes());

    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);

    		Font underlineFont = verdanaFont18.deriveFont(underlineFontAttributes);

    		this.setFont(underlineFont);
    	}

        private void setButtonComponentSettings(JButton button, Color color) {
        	button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusable(false);
            button.setFont(verdanaFont18);
            button.setForeground(color);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

}
