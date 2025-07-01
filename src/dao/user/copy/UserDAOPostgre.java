package dao.user.copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DatabaseManager;
import entity.UserEntity;
import exception.user.InvalidUserException;

public class UserDAOPostgre implements UserDAOInterface {
    UserEntity user;

    public UserDAOPostgre(UserEntity user) {
        this.user = user;
    }

    public boolean checkCredentials(UserEntity user) throws InvalidUserException {
        String query = "SELECT * FROM Utente WHERE Username = ? AND Password = ?";

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, String.valueOf(user.getPassword()));

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidUserException("Errore tecnico durante il controllo delle credenziali.");
        }
    }

    public boolean insertUser(UserEntity user) throws InvalidUserException {
        if (usernameEsisteNelDB(user.getUserName())) {
            throw new InvalidUserException("Username già in uso.");
        }

        int universityId = getUniversityIdByName(user.getUniversity());

        String query = "INSERT INTO Utente (Username, Nome, Cognome, Password, ID_Universita) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, String.valueOf(user.getPassword()));
            stmt.setInt(5, universityId);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidUserException("Errore durante la registrazione dell’utente.");
        }
    }

    private int getUniversityIdByName(String universityName) throws InvalidUserException {
        String query = "SELECT ID_Universita FROM Universita WHERE Nome = ?";

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, universityName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID_Universita");
            } else {
                throw new InvalidUserException("Università non trovata: " + universityName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidUserException("Errore tecnico durante il recupero dell'università.");
        }
    }

    public boolean usernameEsisteNelDB(String username) throws InvalidUserException {
        String query = "SELECT 1 FROM Utente WHERE Username = ?";

        try (
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidUserException("Errore tecnico durante il controllo dello username.");
        }
    }
}
