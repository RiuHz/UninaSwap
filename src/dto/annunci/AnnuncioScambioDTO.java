package dto.annunci;

import java.sql.Date;

import dto.ProdottoDTO;

public class AnnuncioScambioDTO extends AnnuncioDTO {
	
	public AnnuncioScambioDTO(int id, String consegna, Date data, String stato, ProdottoDTO prodotto) {
		super(id, consegna, data, stato, prodotto);
	}

	public String getTipologiaAnnuncio() {
		return "Scambio";
	}
	
}
