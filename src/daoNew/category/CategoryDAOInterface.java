package daoNew.category;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.CategoriaDTO;

public interface CategoryDAOInterface {
	
	public ArrayList<CategoriaDTO> getAllCategories() throws SQLException;
	
}
