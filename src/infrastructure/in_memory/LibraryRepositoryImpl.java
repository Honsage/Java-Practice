package infrastructure.in_memory;

import domain.models.LibraryPosition;
import domain.repositories.LibraryRepository;
import domain.valueobjects.ISBN;

import java.util.*;

public class LibraryRepositoryImpl implements LibraryRepository {

    private final HashMap<ISBN, LibraryPosition> libraryStock;

    public LibraryRepositoryImpl(Collection<LibraryPosition> libraryStock) {
        this.libraryStock = new HashMap<>();
        if (libraryStock != null)
            fillStock(libraryStock);
    }

    public LibraryRepositoryImpl() {
        this(null);
    }

    @Override
    public void save(LibraryPosition libraryPosition) {
        libraryStock.put(libraryPosition.getBook().getIsbn(), libraryPosition);
    }

    @Override
    public void update(LibraryPosition newLibraryPosition) {
        ISBN isbn = newLibraryPosition.getBook().getIsbn();
        Optional<LibraryPosition> oldLibraryPosition = findByIsbn(isbn);
        if (oldLibraryPosition.isEmpty()) throw new NoSuchElementException();
        libraryStock.put(isbn, newLibraryPosition);
    }

    @Override
    public void delete(ISBN isbn) {
        Optional<LibraryPosition> lp = findByIsbn(isbn);
        if (lp.isEmpty()) throw new NoSuchElementException();
        libraryStock.remove(isbn);
    }

    @Override
    public Optional<LibraryPosition> findByIsbn(ISBN isbn) {
        return libraryStock.containsKey(isbn)?
                Optional.of(libraryStock.get(isbn)) :
                Optional.empty();
    }

    @Override
    public List<LibraryPosition> findByTitlePrefix(String titlePrefix) {
        List<LibraryPosition> result = new ArrayList<>();
        for (LibraryPosition lp : libraryStock.values()) {
            if (lp.getBook().getTitle().toLowerCase().startsWith(titlePrefix.toLowerCase()))
                result.add(lp);
        }
        return result;
    }

    @Override
    public List<LibraryPosition> findByAuthor(String author) {
        List<LibraryPosition> result = new ArrayList<>();
        for (LibraryPosition lp : libraryStock.values()) {
            if (lp.getBook().getAuthor().equalsIgnoreCase(author))
                result.add(lp);
        }
        return result;
    }

    @Override
    public List<LibraryPosition> findAll() {
        return new ArrayList<>(libraryStock.values());
    }

    private void fillStock(Collection<LibraryPosition> collection) {
        for (LibraryPosition lp : collection) {
            libraryStock.put(lp.getBook().getIsbn(), lp);
        }
    }
}
