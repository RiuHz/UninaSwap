package dao.university;

import java.util.Vector;

public class UniversityDAOPostgre implements UniversityDAOInterface {

	public Vector<String> getNamesList() {

		// TODO Fai la query per ottenere tutti i nomi di universitàle Università

		Vector<String> p = new Vector<String>();

		p.add("Università di Napoli Federico II");


		return p;
	}
}
