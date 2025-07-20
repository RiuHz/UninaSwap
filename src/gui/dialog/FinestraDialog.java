package gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ControllerApp;

public abstract class FinestraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 18);
	protected final Color nero = new Color(0x262626);

	public FinestraDialog(JFrame framePadre, String titolo, ControllerApp controller) {
		super(framePadre, titolo, true);
		
		setImpostazioniDialog(framePadre);
	}
	
	protected abstract JPanel creaPannelloDialog();
	protected abstract JButton bottoneInterazione(ControllerApp controller);
	
	protected abstract void aggiornaDialog();
	
	public void mostraDialog() {
		aggiornaDialog();
		
		setVisible(true);
	};

	public void nascondiDialog() {
		setVisible(false);
	}
	
	private void setImpostazioniDialog(JFrame framePadre) {
		setSize(400, 600);
		setLocationRelativeTo(framePadre);
		setResizable(false);
		setIconImage(getIcona());
	}
	
	private Image getIcona() {
		URL URLIcona = getClass().getResource("/img/icon.png");
		ImageIcon icona = new ImageIcon(URLIcona);

		return icona.getImage();
	}
	
    protected void creaComponenti(ControllerApp controller) {
    	setLayout(new BorderLayout());
    	
        JScrollPane scrollPanel = new JScrollPane(creaPannelloDialog());
        
        add(scrollPanel, BorderLayout.CENTER);
        add(creaPannelloBottone(controller), BorderLayout.SOUTH);
    }
	
    private JPanel creaPannelloBottone(ControllerApp controller) {
    	JPanel pannelloBottone = new JPanel();
    	pannelloBottone.setLayout(new GridBagLayout());
    	pannelloBottone.setBackground(Color.white);
    	
    	JButton bottoneInterazione = bottoneInterazione(controller);
    	
    	pannelloBottone.add(bottoneInterazione);
    	
    	return pannelloBottone;
    }
    
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    protected class UnderlineButton extends JButton {

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
