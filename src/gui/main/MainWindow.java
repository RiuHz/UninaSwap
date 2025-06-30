package gui.main;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.MainController;
import gui.WindowInterface;

public class MainWindow extends JFrame implements WindowInterface {
	
    private static final long serialVersionUID = 1L;

	public MainWindow(MainController controller) {
        
        setWindowSettings();
        
        LogInPanel logIn = new LogInPanel(controller);
        SignUpPanel signUp = new SignUpPanel(controller);
        
        getContentPane().add(logIn, "LogIn");
        getContentPane().add(signUp, "SignUp");
        
        showWindow();
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
    	CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
    	
    	cardLayout.show(getContentPane(), panel);
    }
    
    private void setWindowSettings() {
    	setTitle("Unina Swap");
    	setIconImage(getIcon());
    	setSize(800, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new CardLayout());
    }
    
	private Image getIcon() {
		URL iconURL = getClass().getResource("/img/icon.png");
		ImageIcon icon = new ImageIcon(iconURL);
		
		return icon.getImage();
	}

}
