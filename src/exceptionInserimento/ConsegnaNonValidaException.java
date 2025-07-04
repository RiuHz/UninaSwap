package exceptionInserimento;

public class ConsegnaNonValidaException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ConsegnaNonValidaException(String message) {
        super(message);
    }
}