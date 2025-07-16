package dto;

public class UserDTO {
	
    private String name;
    private String surname;
    private String username;
    public UniversitaDTO university;

    public UserDTO(String name, String surname, String username, UniversitaDTO university) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }
    
    public String getNomeUniversita() {
    	return university.getNome();
    }
    
    public int getIdUniversita() {
    	return university.getId();
    }
    
}
