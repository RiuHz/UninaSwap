package daoNew.proposta;

import java.sql.*;
import java.util.ArrayList;

import dto.UserDTO;
import dto.proposte.PropostaRegaloDTO;
import dto.proposte.PropostaScambioDTO;
import dto.proposte.PropostaVenditaDTO;

public class PropostaDAOPostgre implements PropostaDAOInterface {
	
	private PropostaRegaloDAOPostgre proposteRegaloDAO = new PropostaRegaloDAOPostgre();
	private PropostaVenditaDAOPostgre proposteVenditaDAO = new PropostaVenditaDAOPostgre();
	private PropostaScambioDAOPostgre proposteScambioDAO = new PropostaScambioDAOPostgre();

	@Override
	public ArrayList<PropostaRegaloDTO> getProposteRegalo(UserDTO user) throws SQLException {
		return proposteRegaloDAO.getProposteRegalo(user);
	}
	
	@Override
	public ArrayList<PropostaVenditaDTO> getProposteVendita(UserDTO user) throws SQLException {
		return proposteVenditaDAO.getProposteVendita(user);
	}
	
	@Override
	public ArrayList<PropostaScambioDTO> getProposteScambio(UserDTO user) throws SQLException {
		return proposteScambioDAO.getProposteScambio(user);
	}

	@Override
	public int getNumeroProposte(UserDTO user) throws SQLException {
		return proposteRegaloDAO.getNumeroProposteRegalo(user)
				+ proposteVenditaDAO.getNumeroProposteVendita(user)
				+ proposteScambioDAO.getNumeroProposteScambio(user);
	}
}
