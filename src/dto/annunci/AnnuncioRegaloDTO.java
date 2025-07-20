package dto.annunci;

import java.sql.Date;

import dto.ProdottoDTO;

public class AnnuncioRegaloDTO extends AnnuncioDTO {
	
	public AnnuncioRegaloDTO(int id, String consegna, Date data, String stato, ProdottoDTO prodotto) {
		super(id, consegna, data, stato, prodotto);
	}
	
}
