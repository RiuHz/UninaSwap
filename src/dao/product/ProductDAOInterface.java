package dao.product;

import java.util.List;

import entity.Prodotto;

public interface ProductDAOInterface {

	List<Prodotto> getProdottiLiberiByUsername(String username);

}
