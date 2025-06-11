package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.util.Vector;

public class MainWindow extends JFrame {

    private final Controller controller;
    private final JPanel centerContainer = new JPanel(new GridBagLayout());
    private JPanel loginPanel;
    private JPanel registrationPanel;
    private JLabel errorLabel;

    public MainWindow(Controller controller) {
        this.controller = controller;
        FrameConfigurator.configureMainFrame(this, centerContainer);
        createLoginPanel();
        createRegistrationPanel();
        centerContainer.add(loginPanel);
        registrationPanel.setVisible(false);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setBackground(FrameConfigurator.getBluPrussia());
        loginPanel.setLayout(new GridBagLayout());
        centerContainer.add(loginPanel);

        JPanel content = new JPanel();
        content.setBackground(FrameConfigurator.getBluPrussia());

        JLabel logoLabel = new JLabel(FrameConfigurator.loadAndResizeLogo("/img/logo.png", 150, 150));
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField emailField = new RoundedTextField(20);
        JTextField passwordField = new RoundedTextField(20);
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        RoundedButton loginButton = new RoundedButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 30));

        // Label "Registrati" con HTML per evitare spostamenti in hover
        JLabel registerLabel = new JLabel("<html><span style='text-decoration:none;'>Registrati</span></html>");
        registerLabel.setPreferredSize(new Dimension(200, 30));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerLabel.setForeground(Color.WHITE);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        emailLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        loginButton.addActionListener(e -> {
            controller.validateLogin(emailField.getText(), passwordField.getText());
        });

        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerLabel.setText("<html><span style='text-decoration:underline;'>Registrati</span></html>");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerLabel.setText("<html><span style='text-decoration:none;'>Registrati</span></html>");
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginPanel.setVisible(false);
                registrationPanel.setVisible(true);
            }
        });

        FrameConfigurator.configureGroupLayout(content,
            logoLabel,
            emailLabel,
            emailField,
            passwordLabel,
            passwordField,
            errorLabel,
            loginButton,
            registerLabel
        );

        loginPanel.add(content);
    }


    private void createRegistrationPanel() {
        registrationPanel = new JPanel();
        registrationPanel.setBackground(FrameConfigurator.getBluPrussia());
        registrationPanel.setLayout(new GridBagLayout());
        centerContainer.add(registrationPanel);

        JPanel content = new JPanel();
        content.setBackground(FrameConfigurator.getBluPrussia());

        JLabel regLabel = new JLabel("REGISTRAZIONE");
        regLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));
        regLabel.setForeground(Color.WHITE);

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel universitaLabel = new JLabel("Università:");

        nomeLabel.setForeground(Color.WHITE);
        cognomeLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        universitaLabel.setForeground(Color.WHITE);

        JTextField nomeField = new RoundedTextField(20);
        JTextField cognomeField = new RoundedTextField(20);
        JTextField emailField = new RoundedTextField(20);
        JTextField passwordField = new RoundedTextField(20);

        JComboBox<String> universitaCombo = new JComboBox<>(new Vector<>(controller.getUniversityList()));
        universitaCombo.setPreferredSize(new Dimension(200, 25));

        RoundedButton inviaBtn = new RoundedButton("Invia");
        RoundedButton indietroBtn = new RoundedButton("Indietro");
        inviaBtn.setPreferredSize(new Dimension(200, 30));
        indietroBtn.setPreferredSize(new Dimension(200, 30));

        inviaBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String universita = (String) universitaCombo.getSelectedItem();

            controller.validateRegistration(email, password, nome, cognome, universita);
        });

        indietroBtn.addActionListener(e -> {
            registrationPanel.setVisible(false);
            loginPanel.setVisible(true);
        });

        FrameConfigurator.configureGroupLayout(content,regLabel,nomeLabel,nomeField,cognomeLabel,cognomeField,
        	emailLabel,emailField,
            passwordLabel, passwordField,
            universitaLabel, universitaCombo,
            inviaBtn, indietroBtn
        );

        registrationPanel.add(content);


    }

    public void showInvalidPasswordError() {
        errorLabel.setText("Email o password non validi");
    }

    public void showInvalidUsernameError() {
        JOptionPane.showMessageDialog(this, "Username non esistente", "Errore di Login", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessRegistration() {
        JOptionPane.showMessageDialog(this, "Registrazione completata con successo", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showExistingUsernameError() {
        JOptionPane.showMessageDialog(this, "Username già esistente", "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
    }
}
