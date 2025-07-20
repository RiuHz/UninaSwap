package dao.universita;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UniversitaDTO;

public interface UniversitaDAOInterface {
	
	public ArrayList<UniversitaDTO> getListaNomiUniversita() throws SQLException;

}
