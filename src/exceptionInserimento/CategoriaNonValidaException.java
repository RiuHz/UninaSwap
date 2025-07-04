package exceptionInserimento;

public class CategoriaNonValidaException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaNonValidaException(String message) {
        super(message);
    }
}