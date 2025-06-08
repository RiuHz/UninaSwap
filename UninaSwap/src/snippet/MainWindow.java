import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JTextField emailLoginField;
    private JTextField passwordLoginField;
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Registrati");
    private Controller c1 = new Controller();

    private JPanel loginPanel;
    private JPanel registrationPanel;

    public MainWindow() {
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

        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));

        emailLoginField = new JTextField(15);
        passwordLoginField = new JTextField(15);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailLoginField.getText();
                String password = passwordLoginField.getText();
                c1.validateLogin(email, password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                registrationPanel.setVisible(true);
            }
        });

        GroupLayout layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(titleLabel)
                .addComponent(emailLoginField)
                .addComponent(passwordLoginField)
                .addComponent(loginButton)
                .addComponent(registerButton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addComponent(emailLoginField)
                .addComponent(passwordLoginField)
                .addComponent(loginButton)
                .addComponent(registerButton)
        );
    }

    private void createRegistrationPanel() {
        registrationPanel = new JPanel();

        JTextField nomeField = new JTextField(15);
        JTextField cognomeField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField passwordField = new JTextField(15);
        JComboBox<String> universitaCombo = new JComboBox<>(c1.university());

        JLabel regLabel = new JLabel("REGISTRAZIONE");
        regLabel.setFont(new Font("Tahoma", Font.PLAIN, 21));

        JButton inviaBtn = new JButton("Invia");
        JButton indietroBtn = new JButton("Indietro");

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

        GroupLayout layout = new GroupLayout(registrationPanel);
        registrationPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(regLabel)
                .addComponent(nomeField)
                .addComponent(cognomeField)
                .addComponent(emailField)
                .addComponent(passwordField)
                .addComponent(universitaCombo)
                .addComponent(inviaBtn)
                .addComponent(indietroBtn)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(regLabel)
                .addComponent(nomeField)
                .addComponent(cognomeField)
                .addComponent(emailField)
                .addComponent(passwordField)
                .addComponent(universitaCombo)
                .addComponent(inviaBtn)
                .addComponent(indietroBtn)
        );
    }
    public void showInvalidPasswordError() {
        JOptionPane.showMessageDialog(this, "Email o password non validi", "Errore di Login", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainWindow frame = new MainWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
