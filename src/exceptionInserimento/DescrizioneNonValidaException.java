package exceptionInserimento;

public class DescrizioneNonValidaException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DescrizioneNonValidaException(String message) {
        super(message);
    }
}