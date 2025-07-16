package daoNew.university;

import java.sql.SQLException;
import java.util.Vector;

public interface UniversityDAOInterface {
	
	public Vector<String> getNamesList() throws SQLException;

}
