package gui.maker;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;

import java.util.*;

import javax.swing.*;

abstract class GUIClasses extends GUIVisuals {
		
    private void setTextComponentSettings(JComponent component) {
    	component.setOpaque(false);
    	component.setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 15));
    	
    	component.setFont(FONT);
    	component.setForeground(BLACK);
    }
    
    private Graphics2D createRoundedRectangle(Graphics2D graphic, JComponent component, Color color) {
    	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setColor(color);
		graphic.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);
		
		return graphic;
    }
    
    protected class RoundedTextField extends JTextField {

        private static final long serialVersionUID = 1L;
        private Color color;

		public RoundedTextField(int columns, Color color) {
	        super(columns);
	        
	        this.color = color;
	        setTextComponentSettings(this);
        }

        @Override
        protected void paintComponent(Graphics graphic) {        	
        	Graphics2D rectangular = createRoundedRectangle((Graphics2D) graphic, this, color);
			    
			super.paintComponent(rectangular);
        }
    }
    
    protected class RoundedPasswordField extends JPasswordField {

        private static final long serialVersionUID = 1L;
        private Color color;

		public RoundedPasswordField(int columns, Color color) {
	        super(columns);
	        
	        this.color = color;
	        setTextComponentSettings(this);
        }
 
        @Override
        protected void paintComponent(Graphics graphic) {        	
			Graphics2D rectangle = createRoundedRectangle((Graphics2D) graphic, this, color);
			    
			super.paintComponent(rectangle);
        }
    }
        
    private void setButtonComponentSettings(JButton button, Color color) {
    	button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setFont(FONT);
        button.setForeground(color);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private Graphics2D createRoundedBorder(Graphics2D graphic, JComponent component, Color color) {
    	graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphic.setColor(color);
		graphic.setStroke(new BasicStroke(3));
		graphic.drawRoundRect(0, 0, component.getWidth(), component.getHeight(), 20, 20);
		
		return graphic;
    }
    
    protected class OutlinedButton extends JButton {
    	
    	private static final long serialVersionUID = 1L;
    	private Color color;
    	private Color hoveredColor;
    	
    	public OutlinedButton(String text, Color color, Color hoveredColor) {
    		super(text);
    		
    		this.color = color;
    		this.hoveredColor = hoveredColor;
    		
    		setButtonComponentSettings(this, color);
    		addMouseAnimation();
    	}
    	
        @Override
        protected void paintComponent(Graphics graphic) {
        	Graphics2D background = createRoundedBorder((Graphics2D) graphic, this, color);

            super.paintComponent(background);
        }
        
        private void addMouseAnimation() {
    		this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					OutlinedButton.this.setForeground(hoveredColor);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					OutlinedButton.this.setForeground(color);
				}
        	});
        }
    }
    
    protected class UnderlineButton extends JButton {
    	
    	private static final long serialVersionUID = 1L;
    	
    	public UnderlineButton(String text, Color color) {
    		super(text);
    		
    		setButtonComponentSettings(this, color);
    		setUnderlineText();
    	}
    	
    	private void setUnderlineText() {
    		Map<TextAttribute, Object> underlineFontAttributes = new java.util.HashMap<TextAttribute, Object>(FONT.getAttributes());
    		
    		underlineFontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
    		
    		Font underlineFont = FONT.deriveFont(underlineFontAttributes);
    		
    		this.setFont(underlineFont);
    	}
    }
}
