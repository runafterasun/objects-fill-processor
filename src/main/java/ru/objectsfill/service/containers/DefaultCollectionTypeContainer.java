package ru.objectsfill.service.containers;

import ru.objectsfill.service.interfaces.CollectionTypeContainerService;
import ru.objectsfill.types.collection_type.CollectionTypeFill;
import ru.objectsfill.types.collection_type.FillListCollection;
import ru.objectsfill.types.collection_type.FillSetCollection;
import ru.objectsfill.types.collection_type.MapFill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**

 The DefaultCollectionTypeContainer class implements the CollectionTypeContainerService interface

 and provides a default implementation of the container for CollectionTypeFill instances.

 It contains mappings between Class objects and their respective CollectionTypeFill implementations.
 */
public class DefaultCollectionTypeContainer implements CollectionTypeContainerService {

    private final Map<Class<?>, CollectionTypeFill> container;
    /**

     Constructs a new DefaultCollectionTypeContainer and initializes the container with default mappings.
     */
    public DefaultCollectionTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(List.class, new FillListCollection());
        container.putIfAbsent(Map.class, new MapFill());
        container.putIfAbsent(Set.class, new FillSetCollection());
    }
    /**

     Returns the container that holds the mappings between Class objects and CollectionTypeFill implementations.
     @return The container.
     */
    public Map<Class<?>, CollectionTypeFill> getContainer() {
        return container;
    }

}
