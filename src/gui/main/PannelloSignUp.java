package gui.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import controller.ControllerAccesso;
import dto.UniversitaDTO;

class PannelloSignUp extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private ControllerAccesso controller;
	
	private final Font font = new Font("Verdana", Font.PLAIN, 16);

	private final Color nero = new Color(0x262626);
	private final Color blu = new Color(0x0F3A5F);
	private final Color verde = new Color(0x31E981);
	private final Color rosso = new Color(0xDF2935);

	private final JLabel labelInfo = creaLabel(" ", rosso);
	
	PannelloSignUp(ControllerAccesso controller) {
		this.controller = controller;
		
		setBackground(blu);
		
		creaComponentiFinestra();
	}
	
	private void resettaLabelInfo() {
		labelInfo.setText(" ");
	}

	private void mostraMessaggioErrore(String messaggio) {
		labelInfo.setForeground(rosso);
		labelInfo.setText(messaggio);
	}
	
	private void mostraMessaggioConferma(String messaggio) {
		labelInfo.setForeground(verde);
		labelInfo.setText(messaggio);
	}
	
	private void bottoneSignUpClick(String nome, String cognome, String username, char[] password, UniversitaDTO universita) {
		try {
        	controller.signUpUtente(nome, cognome, username, password, universita);
        	mostraMessaggioConferma("Utente registrato corretamente!");
        } catch (SQLException SQLError) {
        	mostraMessaggioErrore(SQLError.getLocalizedMessage().replace("ERROR: ", "").split("\\n")[0]);
        }
	}
	
    private ArrayList<UniversitaDTO> getListaUniversita() {
    	ArrayList<UniversitaDTO> listaUniversita = new ArrayList<UniversitaDTO>();
    	
    	try {
    		listaUniversita = controller.getListaUniversita();
    	} catch (SQLException SQLError) {
    		mostraMessaggioErrore(SQLError.getMessage());
    	}
    	
    	return listaUniversita;
    }
	   
    private void creaComponentiFinestra() {
    	JLabel labelLogo = getLogoRidimensionato(0.25);
        
    	JLabel labelNome = creaLabel("Nome", Color.white);
        JTextField fieldNome = new RoundedTextField(20, Color.white);
        
        JLabel labelCognome = creaLabel("Cognome", Color.white);
        JTextField fieldCognome = new RoundedTextField(20, Color.white);
        
        JLabel labelUsername = creaLabel("Username", Color.white);
        JTextField fieldUsername = new RoundedTextField(20, Color.white);

        JLabel labelPassword = creaLabel("Password", Color.white);
        JPasswordField fieldPassword = new RoundedPasswordField(20, Color.white);
        
        JLabel labelUniversita = creaLabel("Università", Color.white);
        JComboBox<UniversitaDTO> comboBoxUniversita = creaComboBox(getListaUniversita());

        JButton bottoneSignUp = new OutlinedButton("Registrati", verde);
        JButton bottoneLogIn = new UnderlineButton("Vuoi effettuare l'accesso?", Color.white);

        bottoneSignUp.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	String nome = fieldNome.getText();
            	String cognome = fieldCognome.getText();
            	String username = fieldUsername.getText();
                char[] password = fieldPassword.getPassword();
                UniversitaDTO universita = (UniversitaDTO) comboBoxUniversita.getSelectedItem();
                
                bottoneSignUpClick(nome, cognome, username, password, universita);
            }
        });

        bottoneLogIn.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.mostraPannello("LogIn");
            	resettaLabelInfo();
            }
        });
        
        aggiungiAlLayout(labelLogo, labelNome, fieldNome, labelCognome, fieldCognome,
        		labelUsername, fieldUsername, labelPassword, fieldPassword,
        		labelUniversita, comboBoxUniversita, labelInfo, bottoneSignUp, bottoneLogIn);
        
        collegaGrandezzaComponenti(fieldNome, fieldCognome, fieldUsername, fieldPassword, comboBoxUniversita);
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
    
    private JComboBox<UniversitaDTO> creaComboBox(ArrayList<UniversitaDTO> elementi) {
    	JComboBox<UniversitaDTO> comboBox = new JComboBox<UniversitaDTO>(elementi.toArray(new UniversitaDTO[0]));
    	comboBox.setFont(font);
    	comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	
    	return comboBox;
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

	private JLabel getLogoRidimensionato(double moltiplicatore) {
		URL URLLogo = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(URLLogo);

		int larghezza = (int) (logo.getIconWidth() * moltiplicatore);
		int altezza = (int) (logo.getIconHeight() * moltiplicatore);

		Image logoRidimensionato = logo.getImage().getScaledInstance(larghezza, altezza, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(logoRidimensionato));
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
