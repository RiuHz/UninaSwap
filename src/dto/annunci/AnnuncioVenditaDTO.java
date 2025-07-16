package dto.annunci;

import java.sql.Date;

import dto.ProdottoDTO;

public class AnnuncioVenditaDTO extends AnnuncioDTO {
	private double prezzo;
	
	public AnnuncioVenditaDTO(int id, String consegna, Date data, double prezzo, String stato, ProdottoDTO prodotto) {
		super(id, consegna, data, stato, prodotto);
		
		this.prezzo = prezzo;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public String getTipologiaAnnuncio() {
		return "Vendita";
	}
}
