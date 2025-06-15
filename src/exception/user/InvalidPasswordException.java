package exception.user;

public class InvalidPasswordException extends InvalidUserException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String errorMessage) {
		super(errorMessage);
	}
	
}
