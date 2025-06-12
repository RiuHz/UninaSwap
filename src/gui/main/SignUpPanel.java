package gui.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComponent;
import javax.swing.JLabel;

import controller.Controller;

import gui.GUIMaker;

class SignUpPanel extends GUIMaker {
	
	private Controller controller;
	
	private JPanel panel = createBluePanel();
	private JLabel infoLabel = createWhiteLabel(" ", 14);
    // TODO private ImageIcon logoIcon = new ImageIcon("C:/Users/Faust/OneDrive/Desktop/GitHub/UninaSwap/src/img/logo.png");
	// TODO Percorso locale al progetto?
	
	SignUpPanel(Controller controller) {
		this.controller = controller;
		
		createComponents();
		        
        panel.setVisible(false);
	}
	   
    private void createComponents() {
        JLabel nameLabel = createWhiteLabel("Nome", 14);
        JLabel surnameLabel = createWhiteLabel("Cognome", 14);
        JLabel usernameLabel = createWhiteLabel("Username", 14);
        JLabel passwordLabel = createWhiteLabel("Password", 14);
        JLabel universityLabel = createWhiteLabel("Università", 14);
        
        JTextField nameField = createWhiteRoundedTextField(20);
        JTextField surnameField = createWhiteRoundedTextField(20);
        JTextField usernameField = createWhiteRoundedTextField(20);

        JPasswordField passwordField = createWhiteRoundedPasswordField(20);
        JComboBox<String> universityCombo = new JComboBox<>(controller.getUniversityList());

        JButton signUpButton = createGreenOutlinedButton("Registrati");
        JButton loginButton = createWhiteUnderlineButton("Accedi");

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	String name = nameField.getText();
            	String surname = surnameField.getText();
            	String username = usernameField.getText();
                String password = passwordField.getText(); // TODO Deprecato?
                String university = (String) universityCombo.getSelectedItem();
                // TODO Richiama un metodo per la validazione della registrazione
            }
        });

        loginButton.addMouseListener(new MouseAdapter() {
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
