package objects.fill.service.containers;

import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.collection_type.CollectionTypeFill;
import objects.fill.types.collection_type.FillListCollection;
import objects.fill.types.collection_type.MapFill;
import objects.fill.types.collection_type.FillSetCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultCollectionTypeContainer implements CollectionTypeContainerService {

    private final Map<Class<?>, CollectionTypeFill> container;

    public DefaultCollectionTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(List.class, new FillListCollection());
        container.putIfAbsent(Map.class, new MapFill());
        container.putIfAbsent(Set.class, new FillSetCollection());
    }

    public Map<Class<?>, CollectionTypeFill> getContainer() {
        return container;
    }

}
