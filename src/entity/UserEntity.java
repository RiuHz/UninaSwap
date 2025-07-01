package entity;

public class UserEntity {
    String name;
    String surname;
    String username;
    char[] password;
    String university;

    public UserEntity(String name, String surname, String username, char[] password, String university) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.university = university;
    }

    public UserEntity(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUserName() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUniversity() {
        return university;
    }
}
