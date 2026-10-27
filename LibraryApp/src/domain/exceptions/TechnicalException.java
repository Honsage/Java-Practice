package domain.exceptions;

public abstract class TechnicalException extends RuntimeException {
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
