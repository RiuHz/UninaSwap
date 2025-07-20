package dao.universita.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.DatabaseManager;
import dao.universita.UniversitaDAOInterface;
import dto.UniversitaDTO;

public class UniversitaDAOPostgre extends DatabaseManager implements UniversitaDAOInterface {

	@Override
    public ArrayList<UniversitaDTO> getListaNomiUniversita() throws SQLException {
		Connection db = getConnection();
		
        ArrayList<UniversitaDTO> listaNomi = new ArrayList<UniversitaDTO>();

        String query = "SELECT ID_Universita, Nome FROM Universita ORDER BY Nome";

        PreparedStatement statement = db.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        
        db.close();

        while (result.next()) {
            listaNomi.add(new UniversitaDTO(
            			result.getInt("ID_Universita"),
            			result.getString("Nome")
            		));
        }

        return listaNomi;
    }
}
