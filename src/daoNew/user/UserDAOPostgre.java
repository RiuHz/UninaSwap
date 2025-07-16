package daoNew.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import daoNew.DatabaseManager;
import dto.UniversitaDTO;
import dto.UserDTO;

public class UserDAOPostgre extends DatabaseManager implements UserDAOInterface {

	@Override
	public void create(String name, String surname, String username, char[] password, String university) throws SQLException {
		Connection db = getConnection();
		
		String query = "INSERT INTO Utente (Username, Nome, Cognome, Password, ID_Universita) VALUES (?, ?, ?, ?, (SELECT ID_Universita FROM Universita WHERE Nome = ?))";
		
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, name);
        statement.setString(3, surname);
        statement.setString(4, String.valueOf(password));
        statement.setString(5, university);
        
        try {
        	statement.executeUpdate();
        } catch (PSQLException SQLError) {
        	throw new SQLException("Username " + username + " già in uso!");
        } finally {
        	db.close();
        }
	}
	
	@Override
	public UserDTO logInUser(String username, char[] password) throws SQLException {
		Connection db = getConnection();

		String query = """
					SELECT Username, Utente.Nome, Cognome,
						Universita.ID_Universita, Universita.Nome AS Nome_Universita
					FROM Utente JOIN Universita ON Utente.ID_Universita = Universita.ID_Universita
					WHERE Username = ? AND Password = ?
				""";
		
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, String.valueOf(password));

        ResultSet result = statement.executeQuery();
        
    	db.close();
        
        if (result.next())
        	return new UserDTO(
        			result.getString("Nome"),
        			result.getString("Cognome"),
        			result.getString("Username"),
        			new UniversitaDTO(
    						result.getInt("ID_Universita"),
    						result.getString("Nome_Universita")
    					)
        		);
        else
        	throw new SQLException("Username o Password non validi!");
        
	}

}
