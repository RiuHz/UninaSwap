package daoNew.annuncio;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.annunci.AnnuncioRegaloDTO;
import dto.annunci.AnnuncioScambioDTO;
import dto.annunci.AnnuncioVenditaDTO;

public class AnnuncioDAOPostgre implements AnnuncioDAOInterface {
	
	AnnuncioRegaloDAOPostgre annunciRegaloDAO = new AnnuncioRegaloDAOPostgre();
	AnnuncioVenditaDAOPostgre annunciVenditaDAO = new AnnuncioVenditaDAOPostgre();
	AnnuncioScambioDAOPostgre annunciScambioDAO = new AnnuncioScambioDAOPostgre();
	
	public ArrayList<AnnuncioRegaloDTO> getAnnunciRegaloRecenti(UserDTO user) throws SQLException {
		return annunciRegaloDAO.getAnnunciRegalo(user);
	}
	
	public ArrayList<AnnuncioVenditaDTO> getAnnunciVenditaRecenti(UserDTO user) throws SQLException {
		return annunciVenditaDAO.getAnnunciVendita(user);
	}
	
	public ArrayList<AnnuncioScambioDTO> getAnnunciScambioRecenti(UserDTO user) throws SQLException {
		return annunciScambioDAO.getAnnunciScambio(user);
	}

}
