package domain.exceptions;

public class BookNotFoundException extends LibraryBusinessException {
    public BookNotFoundException(String isbn) {
        super(String.format("Книга с ISBN %s не найдена в библиотеке!", isbn));
    }
}
