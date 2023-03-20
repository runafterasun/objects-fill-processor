package objects.fill.service.containers;

import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.collection_type.FillCollectionType;
import objects.fill.types.collection_type.FillListCollection;
import objects.fill.types.collection_type.FillMap;
import objects.fill.types.collection_type.FillSetCollection;

import java.util.ArrayList;
import java.util.List;

public class DefaultCollectionTypeContainer implements CollectionTypeContainerService {

    private final List<FillCollectionType> container;

    public DefaultCollectionTypeContainer() {
        container = new ArrayList<>();
        container.add(new FillListCollection());
        container.add(new FillMap());
        container.add(new FillSetCollection());
    }

    public List<FillCollectionType> getContainer() {
        return container;
    }

}
