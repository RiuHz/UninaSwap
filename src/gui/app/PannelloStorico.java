package gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.ControllerApp;

class PannelloStorico extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ControllerApp controller;
	
	private JPanel pannelloStorico = new JPanel();
	
	private final Font fontPrimario = new Font("Verdana", Font.PLAIN, 18);
	private final Font fontSecondario = new Font("Verdana", Font.PLAIN, 16);
	private final Color nero = new Color(0x262626);
	
	public PannelloStorico(ControllerApp controller) {
		this.controller = controller;
		
		setImpostazioniPannello();
	}
	
	public void setImpostazioniPannello() {
		setLayout(new BorderLayout());
		add(new JScrollPane(pannelloStorico), BorderLayout.CENTER);
		
		pannelloStorico.setBackground(Color.white);
		
		aggiornaDati();
	}

	public void aggiornaDati() {
		pannelloStorico.removeAll();
		
		try {
			creaComponenti();
		} catch (SQLException SQLError) {
			JOptionPane.showMessageDialog(this, SQLError.getLocalizedMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		pannelloStorico.repaint();
	}
	
	private void creaComponenti() throws SQLException {
		
		int numeroProposteRegaloInviate = controller.getNumeroProposteRegaloInviate();
		int numeroProposteRegaloAccettate = controller.getNumeroProposteRegaloAccettate();
		
		int numeroProposteScambioInviate = controller.getNumeroProposteScambioInviate();
		int numeroProposteScambioAccettate = controller.getNumeroProposteScambioAccettate();
		
		int numeroProposteVenditaInviate = controller.getNumeroProposteVenditaInviate();
		int numeroProposteVenditaAccettate = controller.getNumeroProposteVenditaAccettate();
		
		double minimoAccettatoPropostaVendita = controller.getMinimoAccettatoPropostaVendita();
		double mediaAccettatoPropostaVendita = controller.getMediaAccettatoPropostaVendita();
		double massimoAccettatoPropostaVendita = controller.getMassimoAccettatoPropostaVendita();
		
		JPanel pannelloProposteRegalo = creaPannelloProposteRegalo(numeroProposteRegaloInviate, numeroProposteRegaloAccettate);
		JPanel pannelloProposteScambio = creaPannelloProposteScambio(numeroProposteScambioInviate, numeroProposteScambioAccettate);
		JPanel pannelloProposteVendita = creaPannelloProposteVendita(numeroProposteVenditaInviate, numeroProposteVenditaAccettate, minimoAccettatoPropostaVendita, mediaAccettatoPropostaVendita, massimoAccettatoPropostaVendita);
		ChartPanel graficoStatistiche = creaGrafico(numeroProposteRegaloInviate, numeroProposteRegaloAccettate, numeroProposteScambioInviate, numeroProposteScambioAccettate, numeroProposteVenditaInviate, numeroProposteVenditaAccettate, minimoAccettatoPropostaVendita, mediaAccettatoPropostaVendita, massimoAccettatoPropostaVendita);
		
		aggiungiAlPannelloStorico(pannelloProposteRegalo, pannelloProposteScambio, pannelloProposteVendita, graficoStatistiche);
	}
	
	private JPanel creaPannelloProposteRegalo(int proposteInviate, int proposteAccettate) {
		JPanel pannello = new JPanel();
		pannello.setBackground(Color.white);
		
		JLabel propostaRegalo = creaLabel(fontPrimario, "Proposte Regalo");
		JLabel inviate = creaLabel(fontSecondario, "Inviate");
		JLabel numeroInviate = creaLabel(fontSecondario, String.valueOf(proposteInviate));
		JLabel accettate = creaLabel(fontSecondario, "Accettate");
		JLabel numeroAccettate = creaLabel(fontSecondario, String.valueOf(proposteAccettate));
		
        GroupLayout layout = new GroupLayout(pannello);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        gruppoOrizzontale
        .addComponent(propostaRegalo)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(inviate)
        						.addComponent(numeroInviate)

    						)
        				.addGap(50)
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(accettate)
        						.addComponent(numeroAccettate)
        					)
        				)
    		);
        
        gruppoVerticale
        .addComponent(propostaRegalo)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(inviate)
                        .addComponent(numeroInviate)
    				)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(accettate)
                        .addComponent(numeroAccettate)
    				)
        	);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        pannello.setLayout(layout);
		
		return pannello;
	}
	
	private JPanel creaPannelloProposteScambio(int proposteInviate, int proposteAccettate) {
		JPanel pannello = new JPanel();
		pannello.setBackground(Color.white);
		
		JLabel propostaScambio = creaLabel(fontPrimario, "Proposte Scambio");
		JLabel inviate = creaLabel(fontSecondario, "Inviate");
		JLabel numeroInviate = creaLabel(fontSecondario, String.valueOf(proposteInviate));
		JLabel accettate = creaLabel(fontSecondario, "Accettate");
		JLabel numeroAccettate = creaLabel(fontSecondario, String.valueOf(proposteAccettate));
		
        GroupLayout layout = new GroupLayout(pannello);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        gruppoOrizzontale
        .addComponent(propostaScambio)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(inviate)
        						.addComponent(numeroInviate)

    						)
        				.addGap(50)
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(accettate)
        						.addComponent(numeroAccettate)
        					)
    				)
    		);
        
        gruppoVerticale
        .addComponent(propostaScambio)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(inviate)
                        .addComponent(numeroInviate)
    				)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(accettate)
                        .addComponent(numeroAccettate)
    				)
        	);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        pannello.setLayout(layout);
		
		return pannello;
	}
	
	private JPanel creaPannelloProposteVendita(int proposteInviate, int proposteAccettate, double minimoAccettato, double mediaAccettato, double massimoAccettato) {
		JPanel pannello = new JPanel();
		pannello.setBackground(Color.white);
		
		JLabel propostaScambio = creaLabel(fontPrimario, "Proposte Vendita");
		JLabel inviate = creaLabel(fontSecondario, "Inviate");
		JLabel numeroInviate = creaLabel(fontSecondario, String.valueOf(proposteInviate));
		JLabel accettate = creaLabel(fontSecondario, "Accettate");
		JLabel numeroAccettate = creaLabel(fontSecondario, String.valueOf(proposteAccettate));
		JLabel minimo = creaLabel(fontSecondario, "Minimo");
		JLabel numeroMinimo = creaLabel(fontSecondario, String.format("%.1f", minimoAccettato));
		JLabel media = creaLabel(fontSecondario, "Medio");
		JLabel numeroMedia = creaLabel(fontSecondario, String.format("%.1f", mediaAccettato));
		JLabel massimo = creaLabel(fontSecondario, "Massimo");
		JLabel numeroMassimo = creaLabel(fontSecondario, String.format("%.1f", massimoAccettato));
		
        GroupLayout layout = new GroupLayout(pannello);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        gruppoOrizzontale
        .addComponent(propostaScambio)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(inviate)
        						.addComponent(numeroInviate)

    						)
        				.addGap(50)
        				.addGroup(
        						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        						.addComponent(accettate)
        						.addComponent(numeroAccettate)
        					)
    				)
        		.addGroup(
        	       		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                		.addGroup(
                				layout.createSequentialGroup()
                				.addGroup(
                						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                						.addComponent(minimo)
                						.addComponent(numeroMinimo)

            						)
                				.addGap(50)
                				.addGroup(
                						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                						.addComponent(media)
                						.addComponent(numeroMedia)
                					)
                				.addGap(50)
                				.addGroup(
                						layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                						.addComponent(massimo)
                						.addComponent(numeroMassimo)
                					)
                			)
    				)
    		);
        
        gruppoVerticale
        .addComponent(propostaScambio)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(inviate)
                        .addComponent(numeroInviate)
    				)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(accettate)
                        .addComponent(numeroAccettate)
    				)
        	)
        .addGroup(
        		layout.createParallelGroup(GroupLayout.Alignment.CENTER)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(minimo)
                        .addComponent(numeroMinimo)
    				)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(media)
                        .addComponent(numeroMedia)
    				)
        		.addGroup(
        				layout.createSequentialGroup()
                        .addComponent(massimo)
                        .addComponent(numeroMassimo)
    				)
        	);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);
        
        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);
        
        pannello.setLayout(layout);
		
		return pannello;
	}
	
	private ChartPanel creaGrafico(int proposteRegaloInviate, int proposteRegaloAccettate, int proposteScambioInviate, int proposteScambioAccettate, int proposteVenditaInviate, int proposteVenditaAccettate, double minimoAccettato, double mediaAccettato, double massimoAccettato) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(proposteRegaloInviate, "Inviate", "Proposte Regalo");
		dataset.setValue(proposteRegaloAccettate, "Accettate", "Proposte Regalo");
		dataset.setValue(proposteScambioInviate, "Inviate", "Proposte Scambio");
		dataset.setValue(proposteScambioAccettate, "Accettate", "Proposte Scambio");
		dataset.setValue(proposteVenditaInviate, "Inviate", "Proposte Vendita");
		dataset.setValue(proposteVenditaAccettate, "Accettate", "Proposte Vendita");
		dataset.setValue(minimoAccettato, "Minimo", "Costi Vendita");
		dataset.setValue(mediaAccettato, "Media", "Costi Vendita");
		dataset.setValue(massimoAccettato, "Massimo", "Costi Vendita");
		
		JFreeChart grafico = ChartFactory.createBarChart("Le mie Statistiche", null, "Totale", dataset, PlotOrientation.VERTICAL, true, true, false);
		
		CategoryPlot plot = grafico.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(nero);
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(0x0F3A5F));
		renderer.setSeriesPaint(1, new Color(0xDF2935));
		renderer.setSeriesPaint(2, new Color(0x9CE37D));
		renderer.setSeriesPaint(3, new Color(0x0B6E4F));
		renderer.setSeriesPaint(4, new Color(0x134611));
		
		ChartPanel pannelloGrafico = new ChartPanel(grafico);
		
		return pannelloGrafico;
	}
	
    /*
     * 
     * Codice per la gestione e creazione della GUI
     * 
     */
	
    private JLabel creaLabel(Font font, String testo) {
    	JLabel label = new JLabel(testo);
    	label.setFont(font);
    	label.setForeground(nero);
        
        return label;
    }
    
    private void aggiungiAlPannelloStorico(JComponent ...componenti) {
        GroupLayout layout = new GroupLayout(pannelloStorico);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup gruppoVerticale = layout.createSequentialGroup();
        GroupLayout.ParallelGroup gruppoOrizzontale = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

        for (JComponent componente : componenti) {
            gruppoVerticale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
            gruppoOrizzontale.addComponent(componente, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE);
        }

        gruppoOrizzontale.addGap(0, 0, Integer.MAX_VALUE);

        layout.setVerticalGroup(gruppoVerticale);
        layout.setHorizontalGroup(gruppoOrizzontale);

        pannelloStorico.setLayout(layout);
        
        collegaGrandezzaComponenti(componenti);
    }

    private void collegaGrandezzaComponenti(JComponent ...componenti) {
    	GroupLayout layout = (GroupLayout) pannelloStorico.getLayout();

        layout.linkSize(SwingConstants.HORIZONTAL, componenti);
    }

}
