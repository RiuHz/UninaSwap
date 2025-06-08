package entities.user;

public class UserDAO {
	User user = new User();
	
	void setUser(String username) {
		user.setUsername(username);
	}
	
	void setUser(String username, String name, String surname, String university) {
		user.setUsername(username);
		user.setName(name);
		user.setSurname(surname);
		user.setUniversity(university);
	}
	
	public void Create(User user, String password) {
		
	}

	public boolean validLogin(String username, String password) {
		setUser(username);
		
		// TODO fare la query
		
		// Se il login risulta valido allora imposto l'utente per intero
		
		return true;
	}
	
	public boolean validRegistration(String username, String password, String name, String surname, String university) {
		setUser(username, name, surname, university);
		
		// TODO Inserire i criteri per la password
		
		if (invalidUsername()) {
			Create(user, password);
			return true;
		} else
			return false;
	}
	
	public boolean invalidUsername() {
		
		// TODO fare la query
		
		// Se preso l'username da parte dell'utente e risulta invalido, allora true
		
		return true;
	}
	


}
