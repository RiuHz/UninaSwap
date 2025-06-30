package gui.app;

import java.awt.*;

import javax.swing.*;

import controller.AppController;

public class HomePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AppController controller;

	private final Font verdanaFontBold = new Font("Verdana", Font.BOLD, 20);
	
	private final Color black = new Color(0x262626);
	
	HomePanel (AppController controller) {
		this.controller = controller;
		
		createComponents();
		
	}
	
	protected void createComponents() {
		JPanel pageHeader = createPageHeader();
		
		setLayout(new BorderLayout());
		add(pageHeader, BorderLayout.NORTH);
	}
	
	protected JPanel createPageHeader() {
		JPanel pageHeader = new JPanel();
		
		JLabel latestListingsLabel = createBoldLabel("Ultimi Annunci", black);
		JLabel filterLabel = createBoldLabel("Filtri", black);
		
		addToPageHeaderLayout(pageHeader, latestListingsLabel, filterLabel);
		
		return pageHeader;
	}
	
    protected void addToPageHeaderLayout(JPanel header, JComponent ...components) {
        GroupLayout layout = new GroupLayout(header);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        for (int index = 0; index < components.length; index++) {
            verticalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            
            if (index < components.length - 1)
                verticalGroup.addGap(0, 0, Integer.MAX_VALUE);
            
            horizontalGroup.addComponent(components[index], GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }
        
        layout.setVerticalGroup(horizontalGroup);
        layout.setHorizontalGroup(verticalGroup);
        
        header.setLayout(layout);
    }
	
    protected JLabel createBoldLabel(String text, Color color) {
    	JLabel label = new JLabel(text);
    	label.setFont(verdanaFontBold);
    	label.setForeground(color);
        
        return label;
    }
	
}
