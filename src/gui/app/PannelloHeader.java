package gui.app;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.*;

import controller.ControllerApp;

class PannelloHeader extends JPanel {

	private static final long serialVersionUID = 1L;

	private ControllerApp controller;
	
	private JButton bottoneOfferteRicevute;

	private final Font font = new Font("Verdana", Font.PLAIN, 16);
	private final Color blu = new Color(0x0F3A5F);

	PannelloHeader(ControllerApp controller) {
		this.controller = controller;

		setBackground(blu);

		creaComponenti();
	}
	
	public void aggiornaDati() {
		int numeroProposte = getNumeroProposteInAttesa();
		
		if (numeroProposte == 1)
			bottoneOfferteRicevute.setText("Hai " + numeroProposte + " proposta da valutare");
		else
			bottoneOfferteRicevute.setText("Hai " + numeroProposte + " proposte da valutare");
	}
	
	private int getNumeroProposteInAttesa() {
		int numeroOfferte = 0;
		
		try {
			numeroOfferte = controller.getNumeroProposteRicevuteInAttesa();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return numeroOfferte;
	}

	private void creaComponenti() {
		JLabel logo = getLogo(0.25);
		bottoneOfferteRicevute = getBottoneOfferteRicevute(Color.white);
		JLabel labelProfilo = getLabelProfilo(Color.white);

		aggiungiAlLayout(logo, bottoneOfferteRicevute, labelProfilo);

		collegaGrandezzaComponenti(logo, labelProfilo);
		
		aggiornaDati();
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */

	private JLabel getLogo(double moltiplicatore) {
		JLabel logo = getLogoRidimensionato(moltiplicatore);
		logo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logo.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mostra("Home");
            }
        });

        return logo;
	}

	private JButton getBottoneOfferteRicevute(Color colore) {
		JButton bottoneOfferte = new UnderlineButton("", colore);

		bottoneOfferte.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottoneOfferte.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mostra("Proposte");
            }
        });

        return bottoneOfferte;
	}

	private JLabel getLabelProfilo(Color colore) {
		JLabel labelProfilo = creaLabel("Il mio Profilo", Color.white);

		labelProfilo.setCursor(new Cursor(Cursor.HAND_CURSOR));

		labelProfilo.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mostra("Profilo");
            }
        });

		return labelProfilo;
	}

    private void aggiungiAlLayout(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoOrizzontale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoVerticale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        for (int index = 0; index < componenti.length; index++) {
            gruppoOrizzontale.addComponent(componenti[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);

            if (index < componenti.length - 1)
                gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

            gruppoVerticale.addComponent(componenti[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);

        setLayout(layout);
    }

    private void collegaGrandezzaComponenti(JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) this.getLayout();

        layout.linkSize(componenti);
    }

	private JLabel getLogoRidimensionato(double moltiplicatore) {
		URL URLLogo = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(URLLogo);

		int larghezza = (int) (logo.getIconWidth() * moltiplicatore);
		int altezza = (int) (logo.getIconHeight() * moltiplicatore);

		Image logoRidimensionato = logo.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(logoRidimensionato));
	}

    private JLabel creaLabel(String testo, Color colore) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(colore);

        return label;
    }

    private class UnderlineButton extends JButton {

    	private static final long serialVersionUID = 1L;

    	public UnderlineButton(String text, Color color) {
    		super(text);

    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}

    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(font.getAttributes());

    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);

    		Font underlineFont = font.deriveFont(underlineFontAttributes);

    		this.setFont(underlineFont);
    	}

        private void setButtonComponentSettings(JButton button, Color color) {
        	button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusable(false);
            button.setFont(font);
            button.setForeground(color);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
