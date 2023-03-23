package objects.fill.service.containers;

import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.collection_type.CollectionTypeFill;
import objects.fill.types.collection_type.FillListCollection;
import objects.fill.types.collection_type.MapFill;
import objects.fill.types.collection_type.FillSetCollection;

import java.util.ArrayList;
import java.util.List;

public class DefaultCollectionTypeContainer implements CollectionTypeContainerService {

    private final List<CollectionTypeFill> container;

    public DefaultCollectionTypeContainer() {
        container = new ArrayList<>();
        container.add(new FillListCollection());
        container.add(new MapFill());
        container.add(new FillSetCollection());
    }

    public List<CollectionTypeFill> getContainer() {
        return container;
    }

}
