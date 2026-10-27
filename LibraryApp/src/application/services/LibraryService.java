package application.services;

import domain.exceptions.BookAlreadyExistsException;
import domain.exceptions.BookNotAvailableException;
import domain.exceptions.BookNotFoundException;
import domain.models.Book;
import domain.models.LibraryPosition;
import domain.models.PositionStatus;
import domain.repositories.LibraryRepository;
import domain.valueobjects.ISBN;
import domain.valueobjects.PublicationYear;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public void addBook(
            String title,
            String author,
            int publicationYear,
            String isbn
    ) {
        Optional<LibraryPosition> lp = libraryRepository.findByIsbn(new ISBN(isbn));
        if (lp.isPresent()) throw new BookAlreadyExistsException(isbn);

        Book book = new Book(
                title,
                author,
                new ISBN(isbn),
                new PublicationYear(publicationYear)
        );

        LibraryPosition libraryPosition = new LibraryPosition(
                book,
                PositionStatus.AVAILABLE,
                null,
                null
        );

        libraryRepository.save(libraryPosition);
    }

    public void removeBook(String isbn) {
        try {
            libraryRepository.delete(new ISBN(isbn));
        } catch (NoSuchElementException e) {
            throw new BookNotFoundException(isbn);
        }
    }

    public List<LibraryPosition> findBooksByTitlePrefix(String prefix) {
        return libraryRepository.findByTitlePrefix(prefix);
    }

    public List<LibraryPosition> findBooksByAuthor(String author) {
        return libraryRepository.findByAuthor(author);
    }

    public List<LibraryPosition> findAllBooks() {
        return libraryRepository.findAll();
    }

    public void borrowBook(
            String isbn,
            String borrower
    ) {
        Optional<LibraryPosition> oldLibraryPosition = libraryRepository.findByIsbn(new ISBN(isbn));
        if (oldLibraryPosition.isEmpty()) throw new BookNotFoundException(isbn);

        if (oldLibraryPosition.get().getStatus() != PositionStatus.AVAILABLE)
            throw new BookNotAvailableException(isbn);

        LibraryPosition newLibraryPosition = new LibraryPosition(
                oldLibraryPosition.get().getBook(),
                PositionStatus.BORROWED,
                borrower,
                LocalDate.now()
        );

        libraryRepository.update(newLibraryPosition);
    }

    public void returnBook(String isbn) {
        Optional<LibraryPosition> oldLibraryPosition = libraryRepository.findByIsbn(new ISBN(isbn));
        if (oldLibraryPosition.isEmpty()) throw new BookNotFoundException(isbn);

        LibraryPosition newLibraryPosition = new LibraryPosition(
                oldLibraryPosition.get().getBook(),
                PositionStatus.AVAILABLE,
                null,
                null
        );

        libraryRepository.update(newLibraryPosition);
    }
}
