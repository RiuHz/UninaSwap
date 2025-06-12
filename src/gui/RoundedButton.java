package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoundedButton extends JButton {

    private final Color bluPrussia = new Color(0x0F3A5F);
    private final Color hoverBackgroundColor = Color.WHITE;
    private final Color normalForegroundColor = Color.WHITE;
    private final Color hoverForegroundColor = bluPrussia;
    private boolean hovered = false;

    private final int arrotondamento= 15;
    private final int paddingVerticale = 8;
    private final int paddingOrizzontale = 16;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setForeground(normalForegroundColor);
        setFont(getFont().deriveFont(Font.BOLD));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusable(false);

        setBorder(createCompoundBorder(normalForegroundColor));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                setForeground(hoverForegroundColor);
                setBorder(createCompoundBorder(hoverForegroundColor));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                setForeground(normalForegroundColor);
                setBorder(createCompoundBorder(normalForegroundColor));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (hovered) {
            g2.setColor(hoverBackgroundColor);
        } else {
            g2.setColor(new Color(255, 255, 255, 0)); // sfondo bottone default
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arrotondamento, arrotondamento);
        g2.dispose();

        super.paintComponent(g);
    }

    private Border createCompoundBorder(Color borderColor) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            BorderFactory.createEmptyBorder(paddingVerticale, paddingOrizzontale, paddingVerticale, paddingOrizzontale)
        );
    }
}
