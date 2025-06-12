package gui.main;

import java.awt.*;
import javax.swing.*;

import controller.Controller;
import gui.GUIComponent;
import gui.GUIMaker;

public class MainWindow extends GUIMaker implements GUIComponent {

	private Controller controller;
	
	private JFrame frame = new JFrame();

    public MainWindow(Controller controller) {
    	
        this.controller = controller;
        
        setWindowSettings();
        
        LogInPanel logIn = new LogInPanel(controller);
        SignUpPanel signUp = new SignUpPanel(controller);
        
        frame.add(logIn.getPanel());
        frame.add(signUp.getPanel());
        
        show();
        
        // TODO Studiare il card layout
        
    }
    
    public void show() {
    	frame.setVisible(true);
    }
    
    public void hide() {
    	frame.setVisible(false);
    }
    
    private void setWindowSettings() {
    	frame.setTitle("Unina Swap");
    	frame.setIconImage(getIconImage());
    	frame.setSize(800, 600);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new CardLayout());
    }

}
