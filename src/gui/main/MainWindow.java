package gui.main;

import java.awt.CardLayout;

import javax.swing.JFrame;

import controller.Controller;

public class MainWindow {

	private Controller controller;
	private JFrame frame = new JFrame();
	
	// TODO Icona della finestra?

    public MainWindow(Controller controller) {
    	
        this.controller = controller;
        
        setWindowSettings();
        
        LogInPanel login = new LogInPanel(controller);
        SignUpPanel signup = new SignUpPanel(controller);
        
        // TODO aggiungi i pannelli al JFrame
        
        // TODO Studiare il card layout
        
    }
    
    private void setWindowSettings() {
        frame.setTitle("Unina Swap");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
