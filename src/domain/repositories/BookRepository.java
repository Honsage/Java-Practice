package domain.repositories;

import domain.models.Book;
import domain.valueobjects.ISBN;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    void delete(ISBN isbn);

    Optional<Book> findByIsbn(ISBN isbn);
    List<Book> findByTitlePrefix(String titlePrefix);
    List<Book> findByAuthor(String author);
    List<Book> findAll();
}
