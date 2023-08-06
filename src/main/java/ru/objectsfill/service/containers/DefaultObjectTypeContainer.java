package ru.objectsfill.service.containers;

import ru.objectsfill.service.interfaces.ObjectTypeContainerService;
import ru.objectsfill.types.object_type.EnumFill;
import ru.objectsfill.types.object_type.ObjectTypeFill;

import java.util.HashMap;
import java.util.Map;
/**

 The DefaultObjectTypeContainer class implements the ObjectTypeContainerService interface

 and provides a default implementation of the container for ObjectTypeFill instances.

 It contains mappings between Class objects and their respective ObjectTypeFill implementations.
 */
public class DefaultObjectTypeContainer implements ObjectTypeContainerService {

    private final Map<Class<?>, ObjectTypeFill> container;
    /**

     Constructs a new DefaultObjectTypeContainer and initializes the container with default mappings.
     */
    public DefaultObjectTypeContainer() {
        container = new HashMap<>();
        container.putIfAbsent(Enum.class, new EnumFill());

    }
    /**

     Returns the container that holds the mappings between Class objects and ObjectTypeFill implementations.
     @return The container.
     */
    public Map<Class<?>,ObjectTypeFill> getContainer() {
        return container;
    }

}
