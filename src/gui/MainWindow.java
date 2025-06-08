package gui;

import javax.swing.*;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MainWindow extends JFrame {
    private JTextField emailLoginField;
    private JTextField passwordLoginField;
    private JLabel errorLabel;
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Registrati");
    private Controller c1;
    JComboBox<String> universitaCombo;
    Color bluPrussia = new Color(0x0F3A5F);
    //ImageIcon logoIcon = new ImageIcon("C:/Users/Faust/OneDrive/Desktop/GitHub/UninaSwap/src/img/logo.png");
    //JLabel logoLabel = new JLabel(logoIcon);


    private JPanel loginPanel;
    private JPanel registrationPanel;

    public MainWindow(Controller controller) {
        this.c1 = controller;
        setTitle("Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(637, 546);
        setLocationRelativeTo(null);

        // Pannelli
        createLoginPanel();
        createRegistrationPanel();

        getContentPane().setLayout(null);

        loginPanel.setBounds(0, 0, 637, 546);
        registrationPanel.setBounds(0, 0, 637, 546);

        getContentPane().add(loginPanel);
        getContentPane().add(registrationPanel);
        registrationPanel.setVisible(false);
    }
    private void createLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setBackground(bluPrussia);

        loginPanel.setLayout(new GridBagLayout());

        JPanel contenutoLogin = new JPanel();
        contenutoLogin.setBackground(bluPrussia);

        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        emailLoginField = new JTextField(20);
        passwordLoginField = new JTextField(20);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        loginButton.setPreferredSize(new Dimension(200, 30));
        registerButton.setPreferredSize(new Dimension(200, 30));

        loginButton.addActionListener(e -> {
            String email = emailLoginField.getText();
            String password = passwordLoginField.getText();
            c1.validateLogin(email, password);
        });

        registerButton.addActionListener(e -> {
            loginPanel.setVisible(false);
            registrationPanel.setVisible(true);
        });

        GroupLayout layout = new GroupLayout(contenutoLogin);
        contenutoLogin.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        titleLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        errorLabel.setForeground(Color.RED);


        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
               // .addComponent(logoLabel)
                .addComponent(titleLabel)
                .addComponent(emailLabel)
                .addComponent(emailLoginField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordLabel)
                .addComponent(passwordLoginField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(errorLabel)
                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addComponent(emailLabel)
                .addComponent(emailLoginField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordLabel)
                .addComponent(passwordLoginField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(errorLabel)
                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        );

        loginPanel.add(contenutoLogin);
    }
    private void createRegistrationPanel() {
        registrationPanel = new JPanel();
        registrationPanel.setBackground(bluPrussia);
        registrationPanel.setLayout(new GridBagLayout());

        JPanel contenutoRegistrazione = new JPanel();
        contenutoRegistrazione.setBackground(bluPrussia);


        JLabel regLabel = new JLabel("REGISTRAZIONE");
        regLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel universitaLabel = new JLabel("Università:");

        JTextField nomeField = new JTextField(20);
        JTextField cognomeField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField passwordField = new JTextField(20);
        universitaCombo = new JComboBox<>(new Vector<>(c1.getUniversityList()));
        universitaCombo.setPreferredSize(new Dimension(200, 25));

        JButton inviaBtn = new JButton("Invia");
        JButton indietroBtn = new JButton("Indietro");
        inviaBtn.setPreferredSize(new Dimension(200, 30));
        indietroBtn.setPreferredSize(new Dimension(200, 30));

        inviaBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String universita = (String) universitaCombo.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Registrazione completata (simulata)");
        });

        indietroBtn.addActionListener(e -> {
            registrationPanel.setVisible(false);
            loginPanel.setVisible(true);
        });

        GroupLayout layout = new GroupLayout(contenutoRegistrazione);
        contenutoRegistrazione.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        regLabel.setForeground(Color.WHITE);
        nomeLabel.setForeground(Color.WHITE);
        cognomeLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        universitaLabel.setForeground(Color.WHITE);


        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(regLabel)
                .addComponent(nomeLabel)
                .addComponent(nomeField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(cognomeLabel)
                .addComponent(cognomeField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(emailLabel)
                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordLabel)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(universitaLabel)
                .addComponent(universitaCombo, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(inviaBtn, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(indietroBtn, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(regLabel)
                .addComponent(nomeLabel)
                .addComponent(nomeField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(cognomeLabel)
                .addComponent(cognomeField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(emailLabel)
                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(passwordLabel)
                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(universitaLabel)
                .addComponent(universitaCombo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addComponent(inviaBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(indietroBtn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        );

        registrationPanel.add(contenutoRegistrazione);
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
