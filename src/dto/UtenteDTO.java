package dto;

public class UtenteDTO {
	
    private String nome;
    private String cognome;
    private String username;
    private UniversitaDTO universita;

    public UtenteDTO(String nome, String cognome, String username, UniversitaDTO universita) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.universita = universita;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }
    
    public UniversitaDTO getUniversita() {
    	return universita;
    }
    
    @Override
    public String toString() {
    	return nome + " " + cognome + " (" + username + ")";
    }

}
