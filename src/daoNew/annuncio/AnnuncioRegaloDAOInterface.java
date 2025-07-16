package daoNew.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.annunci.AnnuncioRegaloDTO;

interface AnnuncioRegaloDAOInterface {
	
	public ArrayList<AnnuncioRegaloDTO> getAnnunciRegalo(UserDTO user) throws SQLException;
	
}
