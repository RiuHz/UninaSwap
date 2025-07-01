package entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CardEntity {
    private String nomeProdotto;
    private byte[] immagine;
    private String consegna;
    private String descrizione;
    private String categoria;
    private String tipo;

    public CardEntity(String nomeProdotto, byte[] immagine, String consegna, String descrizione, String categoria, String tipo) {
        this.nomeProdotto = nomeProdotto;
        this.immagine = immagine;
        this.consegna = consegna;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public String getNomeProdotto() { return nomeProdotto; }
    public byte[] getImmagine() { return immagine; }
    public String getConsegna() { return consegna; }
    public String getDescrizione() { return descrizione; }
    public String getCategoria() { return categoria; }
    public String getTipo() { return tipo; }

    public ImageIcon getImageIcon() {
        try {
            if (immagine == null || immagine.length == 0) {
                System.out.println("Immagine nulla o vuota");
                return null;
            }

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(immagine));
            if (bufferedImage == null) {
                System.out.println("ImageIO.read ha restituito null. L'immagine potrebbe essere corrotta o in un formato non riconosciuto.");
                return null;
            }

            return new ImageIcon(bufferedImage);
        } catch (Exception e) {
            System.out.println("Errore durante la decodifica dell'immagine:");
            e.printStackTrace();
            return null;
        }
    }

}
