package exception;

public class InvalidUsernameException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidUsernameException(String errorMessage) {
		super(errorMessage);
	}
	
}
