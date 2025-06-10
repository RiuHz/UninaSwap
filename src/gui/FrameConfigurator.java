package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.net.URL;
import java.util.Vector;

public class FrameConfigurator {
    private static final Color bluPrussia = new Color(0x0F3A5F);

    public static void configureMainFrame(JFrame frame, JPanel centerContainer) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(637, 546);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout());

        centerContainer.setBackground(bluPrussia);
        frame.getContentPane().add(centerContainer, BorderLayout.CENTER);
    }

    public static Color getBluPrussia() {
        return bluPrussia;
    }

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
    public static void configureGroupLayout(JPanel panel, JComponent... components) {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        for (JComponent comp : components) {
            verticalGroup.addComponent(comp);
            horizontalGroup.addComponent(comp);
        }

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
    }

}
