package domain.exceptions;

public class BookNotAvailableException extends BusinessException {
    public BookNotAvailableException(String isbn) {
        super(String.format("Книга с ISBN %s уже выдана!", isbn));
    }
}
