package ru.objectsfill.service.containers;

import ru.objectsfill.service.interfaces.ObjectTypeContainerService;
import ru.objectsfill.types.object_type.EnumFill;
import ru.objectsfill.types.object_type.ObjectTypeFill;

import java.util.HashMap;
import java.util.Map;

/**
 * Default registry of {@link ObjectTypeFill} implementations for built-in complex types.
 * Currently registers only Enum handling.
 */
public class DefaultObjectTypeContainer implements ObjectTypeContainerService {

    private final Map<Class<?>, ObjectTypeFill> container;

    /**
     * Constructs the container and registers the default Enum type handler.
     */
    public DefaultObjectTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(Enum.class, new EnumFill());

    }

    /**
     * {@inheritDoc}
     */
    public Map<Class<?>,ObjectTypeFill> getContainer() {
        return container;
    }

}
