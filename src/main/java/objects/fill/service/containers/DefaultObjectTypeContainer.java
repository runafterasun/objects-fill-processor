package objects.fill.service.containers;

import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.object_type.EnumFill;
import objects.fill.types.object_type.ObjectTypeFill;

import java.util.ArrayList;
import java.util.List;

public class DefaultObjectTypeContainer implements ObjectTypeContainerService {

    private final List<ObjectTypeFill> container;

    public DefaultObjectTypeContainer() {
        container = new ArrayList<>();
        container.add(new EnumFill());

    }

    public List<ObjectTypeFill> getContainer() {
        return container;
    }

}
