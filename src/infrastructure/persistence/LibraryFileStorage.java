package infrastructure.persistence;

import domain.exceptions.PersistenceException;
import domain.models.LibraryPosition;
import domain.ports.PersistentStorage;

import java.io.*;
import java.util.Collection;

public class LibraryFileStorage implements PersistentStorage<LibraryPosition> {
    private String filepath;

    public LibraryFileStorage(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void dumpCollection(Collection<LibraryPosition> collection) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(collection);
        } catch (IOException e) {
            throw new PersistenceException("Не удалось сохранить данные библиотеки на диск", e);
        }
    }

    @Override
    public Collection<LibraryPosition> loadCollection() {
        Collection<LibraryPosition> collection;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            collection = (Collection<LibraryPosition>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Не удалось загрузить данные библиотеки с диска", e);
        }
        return collection;
    }
}
