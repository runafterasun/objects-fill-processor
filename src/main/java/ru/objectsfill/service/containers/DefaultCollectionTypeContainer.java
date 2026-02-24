package ru.objectsfill.service.containers;

import ru.objectsfill.service.interfaces.CollectionTypeContainerService;
import ru.objectsfill.types.collection_type.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Default registry of {@link CollectionTypeFill} implementations for built-in collection types:
 * List, Set, Map, and Stream.
 */
public class DefaultCollectionTypeContainer implements CollectionTypeContainerService {

    private final Map<Class<?>, CollectionTypeFill> container;

    /**
     * Constructs the container and registers all built-in collection type handlers.
     */
    public DefaultCollectionTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(List.class, new FillListCollection());
        container.putIfAbsent(Map.class, new MapFill());
        container.putIfAbsent(Set.class, new FillSetCollection());
        container.putIfAbsent(Stream.class, new FillRawStream());
    }

    /**
     * {@inheritDoc}
     */
    public Map<Class<?>, CollectionTypeFill> getContainer() {
        return container;
    }

}
