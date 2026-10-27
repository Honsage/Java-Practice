import application.services.LibraryService;
import domain.exceptions.PersistenceException;
import domain.models.LibraryPosition;
import domain.ports.PersistentStorage;
import domain.repositories.LibraryRepository;
import infrastructure.in_memory.LibraryRepositoryImpl;
import infrastructure.persistence.LibraryFileStorage;

import java.util.Collection;

public class LibraryContext {
    private final PersistentStorage<LibraryPosition> storage;
    private final LibraryRepository repository;
    private final LibraryService service;

    public LibraryContext() {
        this.storage = new LibraryFileStorage("library.dat");

        Collection<LibraryPosition> libraryContent = loadData();

        this.repository = new LibraryRepositoryImpl(libraryContent);
        this.service = new LibraryService(repository);
    }

    public LibraryService getService() {
        return service;
    }

    public void saveData() {
        try {
            storage.dumpCollection(repository.findAll());
        } catch (PersistenceException e) {
            System.err.println(e.getMessage());
        }
    }

    private Collection<LibraryPosition> loadData() {
        try {
            return storage.loadCollection();
        } catch (PersistenceException e) {
            return null;
        }
    }
}
