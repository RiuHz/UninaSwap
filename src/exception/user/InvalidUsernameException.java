package exception.user;

public class InvalidUsernameException extends InvaldUserException {

	private static final long serialVersionUID = 1L;

	public InvalidUsernameException(String errorMessage) {
		super(errorMessage);
	}
	
}
