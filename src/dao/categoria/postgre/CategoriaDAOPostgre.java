package dao.categoria.postgre;

import java.sql.*;
import java.util.ArrayList;

import dao.DatabaseManager;
import dao.categoria.CategoriaDAOInterface;
import dto.CategoriaDTO;

public class CategoriaDAOPostgre extends DatabaseManager implements CategoriaDAOInterface {

    @Override
    public ArrayList<CategoriaDTO> getListaCategorie() throws SQLException {
    	Connection db = getConnection();
    	
        ArrayList<CategoriaDTO> listaCategorie = new ArrayList<CategoriaDTO>();

        String query = "SELECT ID_Categoria, Nome FROM Categoria ORDER BY Nome";

	    PreparedStatement statement = db.prepareStatement(query);
	    
	    ResultSet result = statement.executeQuery();
	    
	    db.close();
	
	    while (result.next()) {   
	        listaCategorie.add(new CategoriaDTO(
	        		result.getInt("ID_Categoria"),
	        		result.getString("Nome")
	        	));
	    }

        return listaCategorie;
    }

}
