package dao.university;

import java.util.ArrayList;

public class UniversityDAOPostgre implements UniversityDAOInterface {

	public ArrayList<String> getNamesList() {

		// TODO Fai la query per ottenere tutti i nomi di universitàle Università

		ArrayList<String> p = new ArrayList<String>();

		p.add("Università di Napoli Federico II");


		return p;
	}
}
