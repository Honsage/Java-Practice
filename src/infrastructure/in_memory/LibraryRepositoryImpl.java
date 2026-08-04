package infrastructure.in_memory;

import domain.models.LibraryPosition;
import domain.repositories.LibraryRepository;
import domain.valueobjects.ISBN;

import java.util.*;

public class LibraryRepositoryImpl implements LibraryRepository {
    private final List<LibraryPosition> libraryStock;
    private final HashMap<ISBN, LibraryPosition> isbnIndex;

    public LibraryRepositoryImpl(Collection<LibraryPosition> libraryStock) {
        this.libraryStock = (libraryStock == null) ?
                new ArrayList<>() : new ArrayList<>(libraryStock);
        this.isbnIndex = new HashMap<>();
        buildIsbnIndex();
    }

    public LibraryRepositoryImpl() {
        this(null);
    }

    @Override
    public void save(LibraryPosition libraryPosition) {
        libraryStock.add(libraryPosition);
        isbnIndex.put(libraryPosition.getBook().getIsbn(), libraryPosition);
    }

    @Override
    public void delete(ISBN isbn) {
        Optional<LibraryPosition> lp = findByIsbn(isbn);
        if (lp.isEmpty()) return;
        libraryStock.remove(lp.get());
        isbnIndex.remove(isbn);
    }

    @Override
    public Optional<LibraryPosition> findByIsbn(ISBN isbn) {
        if (isbnIndex != null)
            return isbnIndex.containsKey(isbn)?
                    Optional.of(isbnIndex.get(isbn)) :
                    Optional.empty();
        for (LibraryPosition lp : libraryStock) {
            if (lp.getBook().getIsbn().equals(isbn)) return Optional.of(lp);
        }
        return Optional.empty();
    }

    @Override
    public List<LibraryPosition> findByTitlePrefix(String titlePrefix) {
        List<LibraryPosition> result = new ArrayList<>();
        for (LibraryPosition lp : libraryStock) {
            if (lp.getBook().getTitle().toLowerCase().startsWith(titlePrefix.toLowerCase()))
                result.add(lp);
        }
        return result;
    }

    @Override
    public List<LibraryPosition> findByAuthor(String author) {
        List<LibraryPosition> result = new ArrayList<>();
        for (LibraryPosition lp : libraryStock) {
            if (lp.getBook().getAuthor().equalsIgnoreCase(author))
                result.add(lp);
        }
        return result;
    }

    @Override
    public List<LibraryPosition> findAll() {
        return libraryStock;
    }

    private void buildIsbnIndex() {
        for (LibraryPosition lp : libraryStock) {
            isbnIndex.put(lp.getBook().getIsbn(), lp);
        }
    }
}
