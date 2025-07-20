package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseManager {
	
	// TODO FAUSTONEEEE Cambia le credenziali
	private String URL = "jdbc:postgresql://localhost:5432/UninaSwap";
	private String User = "postgres";
	private String Password = "postgres";

    protected Connection getConnection() throws SQLException {
        try {
        	return DriverManager.getConnection(URL, User, Password);
        } catch (SQLException SQLError) {
        	throw new SQLException("Connessione al DB non riuscita");
        }
    }
    
}