package objects.fill.service;

import objects.fill.object_param.Fill;
import objects.fill.service.containers.DefaultCollectionTypeContainer;
import objects.fill.service.interfaces.CollectionTypeContainerService;
import objects.fill.types.array.FillArray;
import objects.fill.types.collection_type.CollectionTypeFill;
import objects.fill.utils.ScanningForClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static objects.fill.service.ElementCreationService.DEFAULT_LOCAL_CLASS_CREATION_PATH;
import static objects.fill.service.ElementCreationService.findClassInContainer;

/**
 * Фабрика генерации случайных коллекций. Должна проходить по всему дереву зависимостей.
 */
public class CollectionElementCreationService {
    private static final Set<CollectionTypeFill> containerCollectionType = new HashSet<>();

    public CollectionElementCreationService() {
        findLocalContainerForCollectionType();
        containerCollectionType.addAll(new DefaultCollectionTypeContainer().getContainer());
    }

    public Object generateCollection(Field field, Fill fill) {

        Type types = field.getGenericType();
        if (types instanceof ParameterizedType pType && (fill.getGenericType() == null)) {
                fill.setGenericType(pType.getActualTypeArguments());
        }

        Class<?> type = field.getType();
        Optional<CollectionTypeFill> classForCollectionType = findClassInContainer(type, containerCollectionType);

        if(classForCollectionType.isPresent()) {
            return classForCollectionType.get().generate(field, fill);
        }
        if(type.isArray()) {
            return new FillArray().createArray(type, fill);
        }
        return new ElementCreationService().generateSingleValue(type, fill);
    }


    private void findLocalContainerForCollectionType() {
        containerCollectionType.addAll(ScanningForClassUtils.scanClassImplInterface(CollectionTypeContainerService.class, DEFAULT_LOCAL_CLASS_CREATION_PATH)
                .stream()
                .map(CollectionTypeContainerService::getContainer)
                .flatMap(Collection::stream)
                .toList());
    }
}
