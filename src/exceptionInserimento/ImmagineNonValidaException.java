package exceptionInserimento;

public class ImmagineNonValidaException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ImmagineNonValidaException(String message) {
        super(message);
    }
}