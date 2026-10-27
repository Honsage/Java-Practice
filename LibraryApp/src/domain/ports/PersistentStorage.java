package domain.ports;

import java.util.Collection;

public interface PersistentStorage<T> {
    void dumpCollection(Collection<T> collection);
    Collection<T> loadCollection();
}
