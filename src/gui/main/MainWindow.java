package gui.main;

import java.awt.*;
import javax.swing.*;

import controller.Controller;
import gui.WindowInterface;
import gui.maker.GUIMaker;

public class MainWindow extends GUIMaker implements WindowInterface {
	
	private JFrame frame = new JFrame();

    public MainWindow(Controller controller) {
        
        setWindowSettings();
        
        LogInPanel logIn = new LogInPanel(controller);
        SignUpPanel signUp = new SignUpPanel(controller);
        
        frame.getContentPane().add(logIn.getPanel(), "LogIn");
        frame.getContentPane().add(signUp.getPanel(), "SignUp");
        
        show();
    }
    
    @Override
    public void show() {
    	frame.setVisible(true);
    }
    
    @Override
    public void hide() {
    	frame.setVisible(false);
    }
    
    @Override
    public void switchTo(String panel) {
    	CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
    	
    	cardLayout.show(frame.getContentPane(), panel);
    }
    
    private void setWindowSettings() {
    	frame.setTitle("Unina Swap");
    	frame.setIconImage(getIconImage());
    	frame.setSize(800, 600);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new CardLayout());
    }

}
