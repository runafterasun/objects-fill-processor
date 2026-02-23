package ru.objectsfill.service;

import ru.objectsfill.annotation_processor.exceptions.FillException;
import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.containers.DefaultCollectionTypeContainer;
import ru.objectsfill.service.interfaces.CollectionTypeContainerService;
import ru.objectsfill.types.array.FillArray;
import ru.objectsfill.types.collection_type.CollectionTypeFill;
import ru.objectsfill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ru.objectsfill.utils.FieldUtils.getObjectUnaryOperator;

/**
 * Service responsible for generating collection, array, and single-value field contents.
 * Resolves the field's element type, looks up the appropriate {@link CollectionTypeFill} handler,
 * and delegates generation. Falls back to array handling or single-value generation
 * if no collection handler matches.
 */
public class CollectionElementCreationService {
    private final Map<Class<?>, CollectionTypeFill> containerCollectionType = new HashMap<>();

    /**
     * Constructs a new service instance and initializes the collection type registry
     * by scanning for user-defined implementations and adding default mappings.
     */
    public CollectionElementCreationService() {
        findLocalContainerForCollectionType();
        new DefaultCollectionTypeContainer().getContainer().forEach(containerCollectionType::putIfAbsent);
    }

    /**
     * Generates a value for the given field based on its type.
     * Handles parameterized collections (List, Set, Map, Stream), arrays, and plain single values.
     *
     * @param field the field to generate a value for
     * @param fill  the generation parameters
     * @return the generated collection, array, or single value
     */
    public Object generateCollection(Field field, Fill fill) {

        Type types = field.getGenericType();
        if (types instanceof ParameterizedType pType) {
            Arrays.stream(pType.getActualTypeArguments())
                    .findFirst()
                    .ifPresent(genericT -> fill.setGenericType("T", genericT));
        }

        Class<?> type = getTypeClass(field, fill);

        Optional<CollectionTypeFill> classForCollectionType = ElementCreationService.findClassInContainer(type, containerCollectionType);

        if (classForCollectionType.isPresent()) {
            return classForCollectionType.get().generate(field, fill);
        }
        if (type.isArray()) {
            return new FillArray().createArray(type, fill, field);
        }

        return getObjectUnaryOperator(fill, field).apply(new ElementCreationService().generateSingleValue(type, fill));
    }




    /**
     * Resolves the runtime class for a field's type, substituting generic type parameters
     * from the {@link Fill} configuration when the declared type is {@code Object}.
     *
     * @param field the field whose type is being resolved
     * @param fill  the Fill object containing generic type mappings
     * @return the resolved class
     * @throws FillException if a required generic type mapping is not found
     */
    public static Class<?> getTypeClass(Field field, Fill fill) {
        Class<?> type = field.getType();

        try {
            if (type.equals(Object.class))
                type = (Class<?>) fill.getGenericType().get(field.getGenericType().getTypeName());
        } catch (Exception ex) {
            throw new FillException("Generic method not found");
        }
        return type;
    }

    /**
     * Scans for user-defined {@link CollectionTypeContainerService} implementations
     * and registers their handlers in the collection type registry.
     */
    private void findLocalContainerForCollectionType() {
        ScanningForClassUtils.scanClassImplInterface(CollectionTypeContainerService.class, ElementCreationService.DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(CollectionTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerCollectionType::putIfAbsent));
    }
}
