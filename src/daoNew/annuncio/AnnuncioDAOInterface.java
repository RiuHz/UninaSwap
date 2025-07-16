package daoNew.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;

public interface AnnuncioDAOInterface {
	
	// TODO Aggiorna l'interfaccia, faccia di merda
	
	public ArrayList<AnnuncioRegaloDTO> getAnnunciRegaloRecenti(UserDTO user) throws SQLException;
	
	public ArrayList<AnnuncioVenditaDTO> getAnnunciVenditaRecenti(UserDTO user) throws SQLException;
	
	public ArrayList<AnnuncioScambioDTO> getAnnunciScambioRecenti(UserDTO user) throws SQLException;

}
