package daoNew.product;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.ProdottoDTO;
import dto.UserDTO;

public interface ProductDAOInterface {

	public ArrayList<ProdottoDTO> getAllUserProduct(UserDTO user) throws SQLException;
	
	public ArrayList<ProdottoDTO> getAllProductInListings(UserDTO user) throws SQLException;
	
	public ArrayList<ProdottoDTO> gettAllProductsNotInListings(UserDTO user) throws SQLException;
	
}
