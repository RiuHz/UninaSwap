package offerta;

import java.sql.SQLException;
import java.util.List;

import entity.OffertaEntity;

public interface OffertaDAOPostgreInterface {

	List<OffertaEntity> getOfferteRicevute(String usernameDestinatario) throws SQLException;

}
