package daoNew.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.annunci.AnnuncioVenditaDTO;

interface AnnuncioVenditaDAOInterface {

	public ArrayList<AnnuncioVenditaDTO> getAnnunciVendita(UserDTO user) throws SQLException;
	
}
