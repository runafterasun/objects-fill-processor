package objects.fill.service;

import objects.fill.annotation_processor.exceptions.FillException;
import objects.fill.object_param.Fill;
import objects.fill.service.containers.DefaultCollectionTypeContainer;
import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.array.FillArray;
import objects.fill.types.collection_type.CollectionTypeFill;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static objects.fill.service.ElementCreationService.DEFAULT_LOCAL_CLASS_CREATION_PATH;
import static objects.fill.service.ElementCreationService.findClassInContainer;

/**
 * Фабрика генерации случайных коллекций. Должна проходить по всему дереву зависимостей.
 */
public class CollectionElementCreationService {
    private final Map<Class<?>, CollectionTypeFill> containerCollectionType = new HashMap<>();

    public CollectionElementCreationService() {
        findLocalContainerForCollectionType();
        new DefaultCollectionTypeContainer().getContainer().forEach(containerCollectionType::putIfAbsent);
    }

    public Object generateCollection(Field field, Fill fill) {

        Type types = field.getGenericType();
        if (types instanceof ParameterizedType pType) {
            Arrays.stream(pType.getActualTypeArguments())
                    .findFirst()
                    .ifPresent(genericT -> fill.setGenericType("T", genericT));
        }

        Class<?> type = getTypeClass(field, fill);

        Optional<CollectionTypeFill> classForCollectionType = findClassInContainer(type, containerCollectionType);

        if (classForCollectionType.isPresent()) {
            return classForCollectionType.get().generate(field, fill);
        }
        if (type.isArray()) {
            return new FillArray().createArray(type, fill);
        }
        return new ElementCreationService().generateSingleValue(type, fill);
    }

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


    private void findLocalContainerForCollectionType() {
        ScanningForClassUtils.scanClassImplInterface(CollectionTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(CollectionTypeContainerService::getContainer)
                .forEach(container -> container.forEach(containerCollectionType::putIfAbsent));
    }
}
