package dao.annuncio.postgre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import dao.annuncio.AnnuncioDAOInterface;
import dto.ProdottoDTO;
import dto.UtenteDTO;
import dto.annunci.AnnuncioDTO;

public class AnnuncioDAOPostgre implements AnnuncioDAOInterface {
	
	AnnuncioRegaloDAOPostgre AnnuncioRegaloDAO = new AnnuncioRegaloDAOPostgre();
	AnnuncioVenditaDAOPostgre AnnuncioVenditaDAO = new AnnuncioVenditaDAOPostgre();
	AnnuncioScambioDAOPostgre AnnuncioScambioDAO = new AnnuncioScambioDAOPostgre();
	
	@Override
	public ArrayList<AnnuncioDTO> getAnnunciRecenti(UtenteDTO utente) throws SQLException {
		ArrayList<AnnuncioDTO> listaAnnunci = new ArrayList<AnnuncioDTO>();
		
		listaAnnunci.addAll(AnnuncioRegaloDAO.getAnnunciRegaloRecenti(utente));
		listaAnnunci.addAll(AnnuncioVenditaDAO.getAnnunciVenditaRecenti(utente));
		listaAnnunci.addAll(AnnuncioScambioDAO.getAnnunciScambioRecenti(utente));
		
		listaAnnunci.sort(Comparator.comparing(AnnuncioDTO::getData));
		
		return listaAnnunci;
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecenti(UtenteDTO utente) throws SQLException {
		return AnnuncioRegaloDAO.getAnnunciRegaloRecenti(utente);
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciScambioRecenti(UtenteDTO utente) throws SQLException {
		return AnnuncioScambioDAO.getAnnunciScambioRecenti(utente);
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciVenditaRecenti(UtenteDTO utente) throws SQLException {
		return AnnuncioVenditaDAO.getAnnunciVenditaRecenti(utente);
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException {
		ArrayList<AnnuncioDTO> listaAnnunci = new ArrayList<AnnuncioDTO>();
		
		listaAnnunci.addAll(AnnuncioRegaloDAO.getAnnunciRegaloRecentiPerCategoria(utente, categorie));
		listaAnnunci.addAll(AnnuncioScambioDAO.getAnnunciScambioRecentiPerCategoria(utente, categorie));
		listaAnnunci.addAll(AnnuncioVenditaDAO.getAnnunciVenditaRecentiPerCategoria(utente, categorie));
		
		listaAnnunci.sort(Comparator.comparing(AnnuncioDTO::getData));
		
		return listaAnnunci;
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciRegaloRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException {
		return AnnuncioRegaloDAO.getAnnunciRegaloRecentiPerCategoria(utente, categorie);
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciScambioRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException {
		return AnnuncioScambioDAO.getAnnunciScambioRecentiPerCategoria(utente, categorie);
	}

	@Override
	public ArrayList<AnnuncioDTO> getAnnunciVenditaRecentiPerCategoria(UtenteDTO utente, ArrayList<Integer> categorie) throws SQLException {
		return AnnuncioVenditaDAO.getAnnunciVenditaRecentiPerCategoria(utente, categorie);
	}

	@Override
	public void creaAnnuncioRegalo(ProdottoDTO prodotto, String consegna) throws SQLException {
		AnnuncioRegaloDAO.creaAnnuncioRegalo(prodotto, consegna);
	}

	@Override
	public void creaAnnuncioVendita(ProdottoDTO prodotto, String consegna, double prezzo) throws SQLException {
		AnnuncioVenditaDAO.creaAnnuncioVendita(prodotto, consegna, prezzo);
	}

	@Override
	public void creaAnnuncioScambio(ProdottoDTO prodotto, String consegna) throws SQLException {
		AnnuncioScambioDAO.creaAnnuncioScambio(prodotto, consegna);
	}

}
