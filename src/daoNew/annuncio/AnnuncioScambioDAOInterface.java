package daoNew.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.annunci.AnnuncioScambioDTO;

interface AnnuncioScambioDAOInterface {
	
	public ArrayList<AnnuncioScambioDTO> getAnnunciScambio(UserDTO user) throws SQLException;
	
}
