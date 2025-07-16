package daoNew.proposta;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.proposte.PropostaRegaloDTO;

interface PropostaRegaloDAOInterface {

	public ArrayList<PropostaRegaloDTO> getProposteRegalo(UserDTO user) throws SQLException;
	
	public int getNumeroProposteRegalo(UserDTO user) throws SQLException;
	
}
