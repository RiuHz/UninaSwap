package entities.user;

public class User {
	private String username;
	private String name;
	private String surname;
	private String university;

	void setUsername(String username) {
		this.username = username;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	void setSurname(String surname) {
		this.surname = surname;
	}
	
	void setUniversity(String university) {
		this.university = university;
	}
	
	String getUsername() {
		return username;
	}
	
	String getName() {
		return name;
	}
	
	String getSurname() {
		return surname;
	}
	
	String getUniversity() {
		return university;
	}
}
