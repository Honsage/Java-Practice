import application.services.LibraryService;
import domain.exceptions.PersistenceException;
import domain.models.LibraryPosition;
import domain.ports.PersistentStorage;
import domain.repositories.LibraryRepository;
import infrastructure.in_memory.LibraryRepositoryImpl;
import infrastructure.persistence.LibraryFileStorage;
import presentation.controllers.ConsoleController;
import presentation.ui.ConsoleUI;

import java.util.Collection;

public class LibraryApp {
    public static void main(String[] args) {
        PersistentStorage<LibraryPosition> storage = new LibraryFileStorage("library.dat");
        Collection<LibraryPosition> libraryContent;
        try {
            libraryContent = storage.loadCollection();
        } catch (PersistenceException e) {
            libraryContent = null;
        }
        LibraryRepository repository = new LibraryRepositoryImpl(libraryContent);
        LibraryService service = new LibraryService(repository);
        ConsoleController controller = new ConsoleController(service);
        ConsoleUI ui = new ConsoleUI(controller);
        ui.start();
        // При окончании
        try {
            storage.dumpCollection(repository.findAll());
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }
}