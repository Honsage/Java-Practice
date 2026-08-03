package domain.repositories;

import domain.models.Book;
import domain.models.LibraryPosition;
import domain.valueobjects.ISBN;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository {
    void save(LibraryPosition libraryPosition);
    void delete(ISBN isbn);

    Optional<LibraryPosition> findByIsbn(ISBN isbn);
    List<LibraryPosition> findByTitlePrefix(String titlePrefix);
    List<LibraryPosition> findByAuthor(String author);
    List<LibraryPosition> findAll();
}
