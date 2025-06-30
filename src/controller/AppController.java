package controller;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import gui.WindowInterface;
import gui.app.AppWindow;

public class AppController {
	
	// TODO Va fatta una variabile che tiene conto dell'utente loggato?
	
	private MainController mainController;
	private WindowInterface appWindow;
	
	public AppController(MainController mainController) {
		this.mainController = mainController;
		
		appWindow = new AppWindow(this);
		
		appWindow.showWindow();
	}
	
	// TODO Metodo per passare all'altro controller

	public void switchTo(String panel) {
		appWindow.switchTo(panel);
	}
	
	public void test() {
		appWindow.hideWindow();
	}
	
}
