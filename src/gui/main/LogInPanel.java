package gui.main;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Map;

import javax.swing.*;

import controller.MainController;
import exception.user.*;
import session.SessionManager;

class LogInPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private MainController controller;

	private final Font verdanaFont = new Font("Verdana", Font.PLAIN, 16);

	private final Color black = new Color(0x262626);
	private final Color blue = new Color(0x0F3A5F);
	private final Color green = new Color(0x31E981);
	private final Color red = new Color(0xDF2935);

	private JLabel infoLabel = createLabel(" ", red);

	LogInPanel(MainController controller) {
		this.controller = controller;

		setBackground(blue);

		createComponents();
	}

	private void cleanInfoLabel() {
		infoLabel.setText(" ");
	}

	private void showErrorMessage(String message) {
		infoLabel.setText(message);
	}

	private void logInClicked(String username, char[] password) {
		try {
        	controller.userValidation(username, password);
            SessionManager.setUsernameLoggato(username);
            controller.switchTo("Gallery");
        } catch (InvalidUserException error) {
        	showErrorMessage(error.getMessage());
        } catch (Exception error) {
        	showErrorMessage("Si è verificato un problema anomalo!");
        }
	}

	private void createComponents() {
		JLabel logoLabel = getResizedLogo(0.25);

		JLabel usernameLabel = createLabel("Username", Color.white);
        JTextField usernameField = new RoundedTextField(20, Color.white);

        JLabel passwordLabel = createLabel("Password", Color.white);
        JPasswordField passwordField = new RoundedPasswordField(20, Color.white);

        JButton logInButton = new OutlinedButton("Accedi", green);
        JButton signUpButton = new UnderlineButton("Non sei ancora registrato?", Color.white);

        logInButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                logInClicked(username, password);
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.switchTo("SignUp");
            	cleanInfoLabel();
            }
        });

		addToLayout(logoLabel, usernameLabel, usernameField, passwordLabel, passwordField,
				infoLabel, logInButton, signUpButton);

		linkLayoutSize(usernameField, passwordField);
	}

    protected void addToLayout(JComponent ...components) {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        verticalGroup.addGap(0, 0, Integer.MAX_VALUE);
        horizontalGroup.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent component : components) {
            verticalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            horizontalGroup.addComponent(component, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        verticalGroup.addGap(0, 0, Integer.MAX_VALUE);

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);

        this.setLayout(layout);
    }

    protected void linkLayoutSize(JComponent ...components) {
    	GroupLayout layout = (GroupLayout) this.getLayout();

        layout.linkSize(components);
    }

	protected JLabel getResizedLogo(double sizeMultiplier) {
		URL logoURL = getClass().getResource("/img/logo.png");
		ImageIcon logo = new ImageIcon(logoURL);

		int width = (int) (logo.getIconWidth() * sizeMultiplier);
		int height = (int) (logo.getIconHeight() * sizeMultiplier);

		Image resizedLogo = logo.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new JLabel(new ImageIcon(resizedLogo));
	}

    protected JLabel createLabel(String text, Color color) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFont);
    	label.setForeground(color);

        return label;
    }

    protected class RoundedTextField extends JTextField {

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

        	component.setFont(verdanaFont);
        	component.setForeground(black);
        }

        private Graphics2D createRoundedRectangle(Graphics2D graphic, JComponent component, Color color) {
        	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		graphic.setColor(color);
    		graphic.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);

    		return graphic;
        }
    }

    protected class RoundedPasswordField extends JPasswordField {

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

        	component.setFont(verdanaFont);
        	component.setForeground(black);
        }

        private Graphics2D createRoundedRectangle(Graphics2D graphic, JComponent component, Color color) {
        	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		graphic.setColor(color);
    		graphic.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);

    		return graphic;
        }
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

    protected class UnderlineButton extends JButton {

    	private static final long serialVersionUID = 1L;

    	public UnderlineButton(String text, Color color) {
    		super(text);

    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}

    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(verdanaFont.getAttributes());

    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);

    		Font underlineFont = verdanaFont.deriveFont(underlineFontAttributes);

    		this.setFont(underlineFont);
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
    }

}
