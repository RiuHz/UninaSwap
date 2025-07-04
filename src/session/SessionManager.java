package session;

//classe per mantenere traccia dell'utente loggato
public class SessionManager {
    private static String usernameLoggato;
    private static SessionManager instance;

    public static void setUsernameLoggato(String username) {
        usernameLoggato = username;
    }

    public static String getUsernameLoggato() {
        return usernameLoggato;
    }


    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
