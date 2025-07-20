package gui.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.*;

import controller.ControllerAccesso;

class PannelloLogIn extends JPanel {

	private static final long serialVersionUID = 1L;

	private ControllerAccesso controller;

	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	private final Color nero = new Color(0x262626);
	private final Color blu = new Color(0x0F3A5F);
	private final Color verde = new Color(0x31E981);
	private final Color rosso = new Color(0xDF2935);

	private final JLabel labelInfo = creaLabel(" ", rosso);

	PannelloLogIn(ControllerAccesso controller) {
		this.controller = controller;

		setBackground(blu);

		creaComponentiFinestra();
	}

	private void resettaLabelInfo() {
		labelInfo.setText(" ");
	}

	private void mostraMessaggioErrore(String messaggio) {
		labelInfo.setText(messaggio);
	}

	private void bottoneLogInClick(String username, char[] password) {
		try {
        	controller.logInUtente(username, password);
        } catch (SQLException SQLerror) {
        	mostraMessaggioErrore(SQLerror.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0]);
        }
	}

	private void creaComponentiFinestra() {
		JLabel labelLogo = getLogoRidimensionato(0.25);

		JLabel labelUsername = creaLabel("Username", Color.white);
        JTextField fieldUsername = new RoundedTextField(20, Color.white);

        JLabel labelPassword = creaLabel("Password", Color.white);
        JPasswordField fieldPassword = new RoundedPasswordField(20, Color.white);

        JButton bottoneLogIn = new OutlinedButton("Accedi", verde);
        JButton bottoneSignUp = new UnderlineButton("Non sei ancora registrato?", Color.white);

        bottoneLogIn.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	String username = fieldUsername.getText();
                char[] password = fieldPassword.getPassword();

                bottoneLogInClick(username, password);
            }
        });

        bottoneSignUp.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mostraPannello("SignUp");
            	resettaLabelInfo();
            }
        });

		aggiungiAlLayout(labelLogo, labelUsername, fieldUsername, labelPassword, fieldPassword, labelInfo, bottoneLogIn, bottoneSignUp);

		collegaGrandezzaComponenti(fieldUsername, fieldPassword);
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private JLabel creaLabel(String testo, Color colore) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(colore);

        return label;
    }

	private JLabel getLogoRidimensionato(double moltiplicatore) {
		URL URLLogo = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(URLLogo);

		int larghezza = (int) (logo.getIconWidth() * moltiplicatore);
		int altezza = (int) (logo.getIconHeight() * moltiplicatore);

		Image logoRidimensionato = logo.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(logoRidimensionato));
	}

    private void aggiungiAlLayout(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent componente : componenti) {
            gruppoVerticale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            gruppoOrizzontale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        gruppoVerticale.addGap(0, 0, Integer.MAX_VALUE);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);

        this.setLayout(layout);
    }

    private void collegaGrandezzaComponenti(JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) this.getLayout();

        layout.linkSize(componenti);
    }
    
    private class RoundedTextField extends JTextField {

        private static final long serialVersionUID = 1L;
        private Color color;

		public RoundedTextField(int columns, Color color) {
	        super(columns);

	        this.color = color;
	        setTextComponentSettings(this);
        }

        @Override
        protected void paintComponent(Graphics graphic) {
        	Graphics2D rectangular = createRoundedRectangle((Graphics2D) graphic, this, color);

			super.paintComponent(rectangular);
        }

        private void setTextComponentSettings(JComponent component) {
        	component.setOpaque(false);
        	component.setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 15));

        	component.setFont(font);
        	component.setForeground(nero);
        }

        private Graphics2D createRoundedRectangle(Graphics2D graphic, JComponent component, Color color) {
        	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		graphic.setColor(color);
    		graphic.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);

    		return graphic;
        }
    }

    private class RoundedPasswordField extends JPasswordField {

        private static final long serialVersionUID = 1L;
        private Color color;

		public RoundedPasswordField(int columns, Color color) {
	        super(columns);

	        this.color = color;
	        setTextComponentSettings(this);
        }

        @Override
        protected void paintComponent(Graphics graphic) {
			Graphics2D rectangle = createRoundedRectangle((Graphics2D) graphic, this, color);

			super.paintComponent(rectangle);
        }

        private void setTextComponentSettings(JComponent component) {
        	component.setOpaque(false);
        	component.setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 15));

        	component.setFont(font);
        	component.setForeground(nero);
        }

        private Graphics2D createRoundedRectangle(Graphics2D graphic, JComponent component, Color color) {
        	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		graphic.setColor(color);
    		graphic.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);

    		return graphic;
        }
    }

    private class OutlinedButton extends JButton {

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
