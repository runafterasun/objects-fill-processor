package objects.fill.service.containers;

import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.object_type.FillEnum;
import objects.fill.types.object_type.FillObjectType;

import java.util.ArrayList;
import java.util.List;

public class DefaultObjectTypeContainer implements ObjectTypeContainerService {

    private final List<FillObjectType> container;

    public DefaultObjectTypeContainer() {
        container = new ArrayList<>();
        container.add(new FillEnum());

    }

    public List<FillObjectType> getContainer() {
        return container;
    }

}
