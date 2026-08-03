package domain.exceptions;

public class ValidationException extends LibraryBusinessException {
    public ValidationException(String message) {
        super(message);
    }
}
