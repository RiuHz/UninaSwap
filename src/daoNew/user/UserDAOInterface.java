package daoNew.user;

import java.sql.SQLException;

import dto.UserDTO;

public interface UserDAOInterface {

	public void create(String name, String surname, String username, char[] password, String university) throws SQLException;
	
	public UserDTO logInUser(String username, char[] password) throws SQLException;
	
}
