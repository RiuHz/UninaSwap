package exceptionInserimento;

public class PrezzoNonValidoException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PrezzoNonValidoException(String message) {
        super(message);
    }
}