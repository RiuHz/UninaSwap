package gui.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;

import gui.GUIMaker;

class LogInPanel extends GUIMaker {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ", 14);
	
    // TODO ImageIcon logoIcon = new ImageIcon("C:/Users/Faust/OneDrive/Desktop/GitHub/UninaSwap/src/img/logo.png");
	// TODO Percorso locale al progetto?
	
	LogInPanel(Controller controller) {
		this.controller = controller;
		
		createComponents();
        
        panel.setVisible(true);
	}
	
	private void createComponents() {
        JTextField usernameField = createWhiteRoundedTextField(20);
        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        
        JButton loginButton = createGreenOutlinedButton("Accedi");
        JButton signUpButton = createWhiteUnderlineButton("Registrati");

        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // TODO Richiama un metodo per la validazione dell'accesso
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	// TODO richiama un metodo per il cambio di pannello
            }
        });
		
		// TODO createLayout(panel, logoIcon, ..);
	}
	
    private void createLayout(JPanel panel, JComponent ... components) {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        GroupLayout.ParallelGroup horizontalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        for (JComponent component : components) {
            verticalGroup.addComponent(component);
            horizontalGroup.addComponent(component);
        }

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);
    }
}
