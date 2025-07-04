package entity;

public class OffertaEntity {
    private String tipoOfferta; // "Vendita", "Regalo", "Scambio"
    private String nomeProdotto;
    private byte[] immagine;
    private String usernameProponente;
    private String messaggio;       // solo per regalo
    private Double proposta;        // solo per vendita
    private String stato;

    public OffertaEntity(String tipoOfferta, String nomeProdotto, byte[] immagine,
                         String usernameProponente, String messaggio, Double proposta, String stato) {
        this.tipoOfferta = tipoOfferta;
        this.nomeProdotto = nomeProdotto;
        this.immagine = immagine;
        this.usernameProponente = usernameProponente;
        this.messaggio = messaggio;
        this.proposta = proposta;
        this.stato = stato;
    }

	public byte[]  getImmagine() {
		return immagine;
	}

	public String getNomeProdotto() {
		// TODO Auto-generated method stub
		return nomeProdotto;
	}

	public String getTipoOfferta() {
		// TODO Auto-generated method stub
		return tipoOfferta;
	}

	public String getUsernameProponente() {
		// TODO Auto-generated method stub
		return usernameProponente;
	}

	public String getStato() {
		// TODO Auto-generated method stub
		return stato;
	}

	public Double getProposta() {
		// TODO Auto-generated method stub
		return proposta;
	}

	public String getMessaggio() {
		// TODO Auto-generated method stub
		return messaggio;
	}

}
