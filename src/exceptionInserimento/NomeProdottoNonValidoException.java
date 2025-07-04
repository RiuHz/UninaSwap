package exceptionInserimento;

public class NomeProdottoNonValidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public NomeProdottoNonValidoException(String message) {
        super(message);
    }
}
