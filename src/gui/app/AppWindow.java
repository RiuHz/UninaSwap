package gui.app;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.AppController;
import controller.OffertaController;
import gui.WindowInterface;

public class AppWindow extends JFrame implements WindowInterface {

	private static final long serialVersionUID = 1L;
	private final CardLayout cardLayout;
	private final JPanel mainPanel;
	private AppController controller;
	private  OffertaController controllerOne;

	public AppWindow(AppController controller) {
		this.controller=controller;
		controllerOne=new OffertaController(controller);
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		setWindowSettings();
		setHeaderPanel(controller);
		setWindowMainPanel(controller);
		//modifica
		switchTo("Gallery");

	}

	@Override
	public void showWindow() {
		setVisible(true);
	}

	@Override
	public void hideWindow() {
		setVisible(false);
	}

	@Override
	public void switchTo(String panel) {
		CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
		//il panel offers non può essere creato all'avvio perche per essere popolato ha bisogno di avere lo username della persona loggata dato che più giu l'aggiunta dei panel avviene prima del login il panel riceve null come username loggato quindi lo devo fare dopo
	    if (panel.equals("Offers") && !panelAlreadyAdded("Offers")) {
			addToMainPanel(new OffersPanel(controllerOne,controller), "Offers");
	    }

		cardLayout.show(mainPanel, panel);
	}
//ho aggiunto sti 2
	private boolean panelAlreadyAdded(String name) {
	    for (Component comp : mainPanel.getComponents()) {
	        if (mainPanel.getLayout() instanceof CardLayout layout) {
	            if (name.equals(mainPanel.getClientProperty(comp))) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	private void setWindowSettings() {
		setTitle("Bentornato su Unina Swap!");
		setIconImage(getIcon());
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
	}

	private void setHeaderPanel(AppController controller) {
		HeaderPanel header = new HeaderPanel(controller);

		getContentPane().add(header, BorderLayout.NORTH);
	}

	private void setWindowMainPanel(AppController controller) {
		//mainPanel.setLayout(new CardLayout());

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		addToMainPanel(new HomePanel(controller), "Home");
		addToMainPanel(new InventoryPanel(controller), "Inventory");
		addToMainPanel(new ListingsPanel(controller), "Listings");


		//modifiche
		ProductGalleryPanel galleryPanel = new ProductGalleryPanel(controller, mainPanel, cardLayout);
		addToMainPanel(galleryPanel, "Gallery");


	}

	private void addToMainPanel(JPanel panel, String name) {
    	mainPanel.add(panel, name);
	}

	private Image getIcon() {
		URL iconURL = getClass().getResource("/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);

		return icon.getImage();
	}
}
