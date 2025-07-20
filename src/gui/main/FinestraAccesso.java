package gui.main;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import controller.ControllerAccesso;
import gui.InterfacciaFinestraAccesso;

public class FinestraAccesso extends JFrame implements InterfacciaFinestraAccesso {

    private static final long serialVersionUID = 1L;

	public FinestraAccesso(ControllerAccesso controller) {

        setImpostazioniFinestra();

        PannelloLogIn pannelloLogIn = new PannelloLogIn(controller);
        PannelloSignUp pannelloSignUp = new PannelloSignUp(controller);

        getContentPane().add(pannelloLogIn, "LogIn");
        getContentPane().add(pannelloSignUp, "SignUp");

        mostraFinestra();
    }

    @Override
    public void mostraFinestra() {
    	setVisible(true);
    }

    @Override
    public void nascondiFinestra() {
    	setVisible(false);
    }

    @Override
    public void mostraPannello(String panello) {
    	CardLayout cardLayout = (CardLayout) getContentPane().getLayout();

    	cardLayout.show(getContentPane(), panello);
    }
    
	private Image getIcona() {
		URL URLIcona = getClass().getResource("/img/icon.png");
		ImageIcon icona = new ImageIcon(URLIcona);

		return icona.getImage();
	}

    private void setImpostazioniFinestra() {
    	setTitle("Unina Swap");
    	setIconImage(getIcona());
    	setSize(800, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new CardLayout());
    }

}
