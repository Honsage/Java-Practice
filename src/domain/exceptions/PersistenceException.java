package domain.exceptions;

public class PersistenceException extends TechnicalException {
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
