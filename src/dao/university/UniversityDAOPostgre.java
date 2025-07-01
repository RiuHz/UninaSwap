package dao.university;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import db.DatabaseManager;

public class UniversityDAOPostgre implements UniversityDAOInterface {

    public Vector<String> getNamesList() {
        Vector<String> universities = new Vector<>();

        String query = "SELECT Nome FROM Universita ORDER BY Nome";

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                universities.add(rs.getString("Nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return universities;
    }
}
