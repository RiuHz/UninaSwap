package daoNew.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import daoNew.DatabaseManager;

public class UniversityDAOPostgre extends DatabaseManager implements UniversityDAOInterface {

	@Override
    public Vector<String> getNamesList() throws SQLException {
		Connection db = getConnection();
		
        Vector<String> universities = new Vector<>();

        String query = "SELECT Nome FROM Universita ORDER BY Nome";

        PreparedStatement statement = db.prepareStatement(query);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            universities.add(result.getString("Nome"));
        }

        return universities;
    }
}
