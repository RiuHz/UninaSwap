package dao.categoria;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.CategoriaDTO;

public interface CategoriaDAOInterface {
	
	public ArrayList<CategoriaDTO> getListaCategorie() throws SQLException;
	
}
