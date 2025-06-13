package exception.user;

public abstract class InvaldUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	InvaldUserException(String message) {
		super(message);
	}
	
}
