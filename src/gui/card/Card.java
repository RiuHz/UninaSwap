package gui.card;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dto.ProdottoDTO;

public abstract class Card extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected final Font font = new Font("Verdana", Font.PLAIN, 14);
	protected final Color nero = new Color(0x262626);
	protected final Color blu = new Color(0x0F3A5F);
	
	protected Card() {
		setImpostazioniCard();
	}
	
	private void setImpostazioniCard() {
		setLayout(new GroupLayout(this));
		setBorder(BorderFactory.createLineBorder(blu, 1));
		setBackground(Color.white);
	}

    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected JLabel creaLabel(String testo) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(nero);
        
        return label;
    }
	
	protected JLabel getImmagineRidimensionata(ProdottoDTO prodotto) {
		BufferedImage immagine = byteArrayToImmagine(prodotto.getImmagine());
		ImageIcon icona = new ImageIcon(immagine);
		
		int altezza = (150 * icona.getIconHeight()) / icona.getIconWidth();

		Image immagineRidimensionata = icona.getImage().getScaledInstance(150, altezza, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(immagineRidimensionata));
	}
	
	protected BufferedImage byteArrayToImmagine(byte[] imageBytes) {
		BufferedImage immagine = null;
			
		try {
			immagine = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} catch (IOException IOError) {
			JOptionPane.showMessageDialog(this, "Non è stato possibile caricare alcune immagini di prodotti!", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return immagine;
	}
    
    protected JTextArea creaTextArea(String testo) {
		JTextArea textArea = new JTextArea(testo);
		
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setFocusable(false);
		textArea.setBorder(null);
		textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		textArea.setFont(font);
		textArea.setForeground(nero);
		textArea.setBackground(Color.white);
		
		return textArea;
    }
    
    protected class OutlinedButton extends JButton {

    	private static final long serialVersionUID = 1L;
    	private Color color;

    	public OutlinedButton(String text, Color color) {
    		super(text);

    		this.color = color;

    		setButtonComponentSettings(this, color);
    	}

        @Override
        protected void paintComponent(Graphics graphic) {
        	Graphics2D background = createRoundedBorder((Graphics2D) graphic, this, color);

            super.paintComponent(background);
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

        private Graphics2D createRoundedBorder(Graphics2D graphic, JComponent component, Color color) {
        	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		graphic.setColor(color);
    		graphic.setStroke(new BasicStroke(3));
    		graphic.drawRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);

    		return graphic;
        }
    }
	
}
