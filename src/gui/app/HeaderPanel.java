package gui.app;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.Map;

import javax.swing.*;

import controller.AppController;

class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private AppController controller;

	private final Font verdanaFont = new Font("Verdana", Font.PLAIN, 16);

	private final Color blue = new Color(0x0F3A5F);

	HeaderPanel(AppController controller) {
		this.controller = controller;

		setBackground(blue);

		createComponents();
	}

	// TODO Il logo deve riporate alla home quando cliccato
	// TODO Il notifyButton alla sezione proposte
	// TODO La userDroplist invece apre un menù e in base a cosa si clicca

	private void createComponents() {
		JLabel logo = getClickableLogo(0.25);
		JButton notifyButton = getNotifyButton(Color.white);
		JLabel userDroplist = getUserDropList(Color.white);

		addToLayout(logo, notifyButton, userDroplist);

		linkLayoutSize(logo, userDroplist);
	}

	private JLabel getClickableLogo(double size) {
		JLabel logo = getResizedLogo(size);
		logo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logo.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.switchTo("Home");
            }
        });

        return logo;
	}

	private JButton getNotifyButton(Color color) {
		JButton notifyButton = new UnderlineButton("Offerte Ricevute", color); // TODO Qua dovrei fare una query per vedere il numero di offerte!

		notifyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        notifyButton.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.switchTo("Offers");
            }
        });

        return notifyButton;
	}

	private JLabel getUserDropList(Color color) {
		JLabel userDropList = createLabel("Utente", Color.white); // TODO doveri fare la Droplist! (Utente loggato nel controller?)

		userDropList.setCursor(new Cursor(Cursor.HAND_CURSOR));

		userDropList.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
            	controller.switchTo("Inventory");
            }
        });

		return userDropList;
	}

    protected void addToLayout(JComponent ...components) {
        GroupLayout layout = new GroupLayout(this);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        for (int index = 0; index < components.length; index++) {
            verticalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);

            if (index < components.length - 1)
                verticalGroup.addGap(0, 0, Integer.MAX_VALUE);

            horizontalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        layout.setVerticalGroup(horizontalGroup);
        layout.setHorizontalGroup(verticalGroup);

        setLayout(layout);
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
