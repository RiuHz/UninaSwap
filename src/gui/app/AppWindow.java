package gui.app;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.AppController;
import gui.WindowInterface;

public class AppWindow extends JFrame implements WindowInterface {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel = new JPanel();
	
	public AppWindow(AppController controller) {
		
		setWindowSettings();
		setHeaderPanel(controller);
		setWindowMainPanel(controller);
		
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
		
		cardLayout.show(mainPanel, panel);
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
		mainPanel.setLayout(new CardLayout());
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		addToMainPanel(new HomePanel(controller), "Home");
		addToMainPanel(new InventoryPanel(controller), "Inventory");
		addToMainPanel(new ListingsPanel(controller), "Listings");
		addToMainPanel(new OffersPanel(controller), "Offers");
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
