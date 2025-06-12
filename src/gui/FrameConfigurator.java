package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.net.URL;
import java.util.Vector;

public class FrameConfigurator {
	
	// TODO Cosa succede qui?
    
    public static ImageIcon loadAndResizeLogo(String path, int width, int height) {
        URL resource = FrameConfigurator.class.getResource(path);
        if (resource == null) {
            System.err.println("Logo non trovato nel percorso: " + path);
            return null;
        }
        ImageIcon logoIcon = new ImageIcon(resource);
        Image originalImage = logoIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
}
