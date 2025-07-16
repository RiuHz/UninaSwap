package daoNew.proposta;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;

public interface PropostaDAOInterface {

	public int getNumeroProposte(UserDTO user) throws SQLException;
	
	public ArrayList<PropostaRegaloDTO> getProposteRegalo(UserDTO user) throws SQLException;
	
	public ArrayList<PropostaVenditaDTO> getProposteVendita(UserDTO user) throws SQLException;
	
	public ArrayList<PropostaScambioDTO> getProposteScambio(UserDTO user) throws SQLException;

}
