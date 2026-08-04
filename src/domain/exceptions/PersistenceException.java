package domain.exceptions;

public class PersistenceException extends LibraryBusinessException {
    public PersistenceException(String message) {
        super(message);
    }
}
