package gui.app;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import controller.ControllerApp;
import dto.ProdottoDTO;
import dto.annunci.AnnuncioDTO;
import dto.proposte.PropostaDTO;
import gui.InterfacciaFinestraApp;

public class FinestraApp extends JFrame implements InterfacciaFinestraApp {

	private static final long serialVersionUID = 1L;
	
	private JPanel pannelloCentrale = new JPanel();
	
	private PannelloHeader pannelloHeader;
	private PannelloHome pannelloHome;
	private PannelloProposteRicevute pannelloProposteRicevute;
	private PannelloProfilo pannelloProfilo;
	
	public FinestraApp(ControllerApp controller) {

		setImpostazioniFinestra();
		
		setPannelloHeader(controller);
		setPannelloCentrale(controller);
		
		creaPannelliApp(controller);
		aggiungiPannelliApp();
		
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
	public void mostraPannello(String pannello) {
		CardLayout cardLayout = (CardLayout) pannelloCentrale.getLayout();

		cardLayout.show(pannelloCentrale, pannello);
	}
	
	@Override
	public void aggiornaAnnunci(ArrayList<AnnuncioDTO> listaAnnunci) {
		pannelloHome.aggiornaDati(listaAnnunci);
	}

	@Override
	public void aggiornaInventario(ArrayList<ProdottoDTO> listaProdotti) {
		pannelloProfilo.aggiornaInventario(listaProdotti);
	}

	@Override
	public void aggiornaProposteRicevute(ArrayList<PropostaDTO> listaProposteRicevute) {
		pannelloProposteRicevute.aggiornaDati(listaProposteRicevute);
		pannelloHeader.aggiornaDati();
	}

	@Override
	public void aggiornaProposteInviate(ArrayList<PropostaDTO> listaProposteInviate) {
		pannelloProfilo.aggiornaProposteInviate(listaProposteInviate);
		pannelloProfilo.aggiornaStorico();
	}

	private void setPannelloHeader(ControllerApp controller) {
		pannelloHeader = new PannelloHeader(controller);

		getContentPane().add(pannelloHeader, BorderLayout.NORTH);
	}

	private void setPannelloCentrale(ControllerApp controller) {
		pannelloCentrale.setLayout(new CardLayout());

		getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
	}
	
	private void creaPannelliApp(ControllerApp controller) {
		pannelloHome = new PannelloHome(this, controller);
		pannelloProposteRicevute = new PannelloProposteRicevute(this, controller);
		pannelloProfilo = new PannelloProfilo(this, controller);
	}
	
	private void aggiungiPannelliApp() {
		pannelloCentrale.add(pannelloHome, "Home");
		pannelloCentrale.add(pannelloProposteRicevute, "Proposte");
		pannelloCentrale.add(pannelloProfilo, "Profilo");
	}

	private Image getIcona() {
		URL URLIcona = getClass().getResource("/img/icon.png");
		ImageIcon icona = new ImageIcon(URLIcona);

		return icona.getImage();
	}
	
	private void setImpostazioniFinestra() {
		setTitle("Bentornato su Unina Swap!");
		setIconImage(getIcona());
		setSize(1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		pannelloCentrale.setBackground(Color.white);
	}
	
}
