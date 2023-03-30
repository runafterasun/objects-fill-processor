package objects.fill.service.containers;

import objects.fill.service.interfaces.ObjectTypeContainerService;
import objects.fill.types.object_type.EnumFill;
import objects.fill.types.object_type.ObjectTypeFill;

import java.util.HashMap;
import java.util.Map;

public class DefaultObjectTypeContainer implements ObjectTypeContainerService {

    private final Map<Class<?>, ObjectTypeFill> container;

    public DefaultObjectTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(Enum.class, new EnumFill());

    }

    public Map<Class<?>,ObjectTypeFill> getContainer() {
        return container;
    }

}
