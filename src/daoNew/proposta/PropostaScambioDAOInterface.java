package daoNew.proposta;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.proposte.PropostaScambioDTO;

interface PropostaScambioDAOInterface {

	public int getNumeroProposteScambio(UserDTO user) throws SQLException;
	
	public ArrayList<PropostaScambioDTO> getProposteScambio(UserDTO user) throws SQLException;
	
}
