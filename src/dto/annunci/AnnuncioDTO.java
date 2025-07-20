package dto.annunci;

import java.sql.Date;

import dto.ProdottoDTO;

public abstract class AnnuncioDTO {
	private int id;
	private String consegna;
	private Date data;
	private String stato;
	private ProdottoDTO prodotto;
	
	protected AnnuncioDTO(int id, String consegna, Date data, String stato, ProdottoDTO prodotto) {
		this.id = id;
		this.consegna = consegna;
		this.data = data;
		this.stato = stato;
		this.prodotto = prodotto;
	}
	
	public int getId() {
		return id;
	}
	
	public String getConsegna() {
		return consegna;
	}
	
	public String getData() {
		return data.toString();
	}
	
	public String getStato() {
		return stato;
	}
	
	public ProdottoDTO getProdotto() {
		return prodotto;
	}

}
