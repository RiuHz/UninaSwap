package daoNew.proposta;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UserDTO;
import dto.proposte.PropostaVenditaDTO;

interface PropostaVenditaDAOInterface {
	
	public ArrayList<PropostaVenditaDTO> getProposteVendita(UserDTO user) throws SQLException;
	
	public int getNumeroProposteVendita(UserDTO user) throws SQLException;

}
