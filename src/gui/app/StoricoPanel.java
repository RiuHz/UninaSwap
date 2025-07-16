package gui.app;

import javax.swing.JPanel;

import controller.AppController;

class StoricoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AppController controller;
	
	public StoricoPanel(AppController controller) {
		this.controller = controller;
		
		createComponents();
	}
	
	private void createComponents() {
		
	}

}
