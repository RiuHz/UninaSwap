package controller;

import java.sql.SQLException;
import java.util.List;

import dao.offerta.OffertaDAOPostgre;
import session.SessionManager;
import entity.OffertaEntity;

public class OffertaController {
	OffertaDAOPostgre offertaDAO;

	private AppController mainController;

	public OffertaController(AppController mainController) {
	    this.offertaDAO = new OffertaDAOPostgre();
	    this.mainController = mainController;
	}

	public void tornaAllaGallery() {
	    mainController.switchTo("Gallery");
	}


	public List<OffertaEntity> getOfferteRicevute() throws SQLException {
		String username=SessionManager.getUsernameLoggato();
	    return offertaDAO.getOfferteRicevute(username);
	}



    public OffertaController() {
        this.offertaDAO = new OffertaDAOPostgre();
    }


}
