package gui.app;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JPanel {
    private final Image image;

    public ImagePanel(Image image) {
        this.image = image;
        setPreferredSize(new Dimension(200, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = image.getWidth(this);
            int imgHeight = image.getHeight(this);

            // Calcolo rapporto per mantenere proporzioni
            double widthRatio = (double) panelWidth / imgWidth;
            double heightRatio = (double) panelHeight / imgHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);

            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;

            g.drawImage(image, x, y, drawWidth, drawHeight, this);
        }
    }
}
