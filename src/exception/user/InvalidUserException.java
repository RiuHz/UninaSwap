package exception.user;

public class InvalidUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	InvalidUserException(String message) {
		super(message);
	}
	
}
