package daoNew.category;

import java.sql.*;
import java.util.ArrayList;

import daoNew.DatabaseManager;

import dto.CategoriaDTO;

public class CategoryDAOPostgre extends DatabaseManager implements CategoryDAOInterface {

    @Override
    public ArrayList<CategoriaDTO> getAllCategories() throws SQLException {
    	Connection db = getConnection();
    	
        ArrayList<CategoriaDTO> categorie = new ArrayList<CategoriaDTO>();

        String query = "SELECT ID_Categoria, Nome FROM Categoria ORDER BY Nome";

	    PreparedStatement statement = db.prepareStatement(query);
	    
	    ResultSet result = statement.executeQuery();
	
	    while (result.next()) {
	        int id = result.getInt("ID_Categoria");
	        String nome = result.getString("Nome");
	        
	        categorie.add(new CategoriaDTO(id, nome));
	    }

        return categorie;
    }

}
