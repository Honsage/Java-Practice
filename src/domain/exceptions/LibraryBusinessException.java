package domain.exceptions;

public abstract class LibraryBusinessException extends RuntimeException {
    public LibraryBusinessException(String message) {
        super(message);
    }
}
