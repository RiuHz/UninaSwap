package dao.utente.postgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import dao.DatabaseManager;
import dao.utente.UtenteDAOInterface;
import dto.UniversitaDTO;
import dto.UtenteDTO;

public class UtenteDAOPostgre extends DatabaseManager implements UtenteDAOInterface {

	@Override
	public void creaUtente(String nome, String cognome, String username, char[] password, UniversitaDTO universita) throws SQLException {
		Connection db = getConnection();
		
		String query = "INSERT INTO Utente (Username, Nome, Cognome, Password, ID_Universita) VALUES (?, ?, ?, ?, ?)";
		
        PreparedStatement statement = db.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, nome);
        statement.setString(3, cognome);
        statement.setString(4, String.valueOf(password));
        statement.setInt(5, universita.getId());
   
        try {
        	statement.executeUpdate();
        } catch (PSQLException SQLError) {
        	if (isChiavePrimariaDuplicata(SQLError))
        		throw new SQLException("Username " + username + " già in uso!");
        	else
        		throw SQLError;
        } finally {
        	db.close();
        }
	}
	
	private boolean isChiavePrimariaDuplicata(SQLException SQLError) {
		return SQLError.getSQLState().equals("23505");
	}
	
	@Override
	public UtenteDTO logInUtente(String username, char[] password) throws SQLException {
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
        	return new UtenteDTO(
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
