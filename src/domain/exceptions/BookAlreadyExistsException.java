package domain.exceptions;

public class BookAlreadyExistsException extends LibraryBusinessException {
    public BookAlreadyExistsException(String isbn) {
        super(String.format("Книга с ISBN %s уже добавлена в библиотеку!", isbn));
    }
}
