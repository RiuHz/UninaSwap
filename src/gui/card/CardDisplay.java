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
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public abstract class CardDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected final Font verdanaFont = new Font("Verdana", Font.PLAIN, 14);
	
	private final Color blue = new Color(0x0F3A5F);
	protected final Color black = new Color(0x262626);
	
	protected CardDisplay() {
		setWindowSettings();
	}
	
	private void setWindowSettings() {
		setLayout(new GroupLayout(this));
		setBorder(BorderFactory.createLineBorder(blue, 1));
	}

    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
	protected JLabel getResizedImage() { // TODO prende come parametro un prodotto dal quale ricava l'immagine
		URL logoURL = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(logoURL);
		
		int height = (150 * logo.getIconHeight()) / logo.getIconWidth();

		Image resizedLogo = logo.getImage().getScaledInstance(150, height, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(resizedLogo));
	}
	
    protected JLabel createLabel(String text) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont);
    	label.setForeground(black);
    	label.setMaximumSize(new Dimension(150, 100));
        
        return label;
    }
    
    protected JTextArea createTextArea(String text) {
		JTextArea textArea = new JTextArea(text);
		
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setFocusable(false);
		textArea.setBorder(null);
		textArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		textArea.setFont(verdanaFont);
		textArea.setForeground(black);
		
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
            button.setFont(verdanaFont);
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
